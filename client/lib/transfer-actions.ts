'use server';

import { z } from "zod";
import { UUID } from "crypto";
import { CreateTransferDto } from "@/lib/types";
import { transferApiService } from "@/services/transfer-api-service";
import { revalidatePath } from "next/cache";
import { redirect } from "next/navigation";

const CreateTransferFormSchema = z.object({
  sourceWalletId: z.uuid(),
  targetWalletId: z.uuid(),
  sourceAmount: z.coerce.number().positive("Сумма должна быть положительной"),
  targetAmount: z.coerce.number().positive("Целевая сумма должна быть положительной"),
  transferDate: z.string().min(1, "Дата перевода обязательна"),
  notes: z.string().nullish(),
}).refine((data) => data.sourceWalletId !== data.targetWalletId, {
  message: "Исходный и целевой кошельки должны быть разными",
  path: ["targetWalletId"]
});

export type State = {
  message?: string | null;
}

export async function createTransfer(prevState: State, formData: FormData) {
  const validatedFields = CreateTransferFormSchema.safeParse({
    sourceWalletId: formData.get('sourceWallet'),
    targetWalletId: formData.get('targetWallet'),
    sourceAmount: formData.get('sourceAmount'),
    targetAmount: formData.get('targetAmount'),
    transferDate: formData.get('transferDate'),
    notes: formData.get('notes')
  });

  if (!validatedFields.success) {
    return {
      message: 'Атрибуты формы заполнены некорректно'
    } satisfies State;
  }

  const {
    sourceWalletId,
    targetWalletId,
    sourceAmount,
    targetAmount,
    transferDate,
    notes
  } = validatedFields.data;

  const createTransferDto: CreateTransferDto = {
    sourceWalletId: sourceWalletId as UUID,
    targetWalletId: targetWalletId as UUID,
    sourceAmount: sourceAmount,
    targetAmount: targetAmount,
    transferDate: transferDate,
    notes: notes
  } satisfies CreateTransferDto;

  try {
    await transferApiService.createTransfer(createTransferDto);
  } catch (error) {
    return {
      message: 'Перевод не был выполнен'
    } satisfies State;
  }

  revalidatePath("/transactions");
  redirect("/transactions");
}
