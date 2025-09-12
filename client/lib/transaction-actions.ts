'use server';

import { revalidatePath } from "next/cache";
import { redirect } from "next/navigation";
import { z } from "zod";
import { CreateExpenseTransactionDto } from "@/lib/types";
import { transactionApiService } from '@/services/transaction-api-service';

const CreateExpenseTransactionFormSchema = z.object({
  walletId: z.string(),
  expenseCategoryId: z.string(),
  transactionDate: z.string().min(1, "Start date is required"),
  notes: z.string().nullish(),
  amount: z.coerce.number()
});

export type State = {
  errors?: {
    wallet?: {
      errors?: string[] | null;
    },
    category?: {
      errors?: string[] | null;
    },
    amount?: {
      errors?: string[] | null;
    },
    notes?: {
      errors?: string[] | null;
    }
  };
  message?: string | null;
}

export async function createExpenseTransaction(prevState: State, formData: FormData) {
  const validatedFields = CreateExpenseTransactionFormSchema.safeParse({
    walletId: formData.get('wallet'),
    expenseCategoryId: formData.get('category'),
    transactionDate: formData.get('transactionDate'),
    amount: formData.get('amount'),
    notes: formData.get('notes')
  });

  console.log('validatedFields', validatedFields);

  if (!validatedFields.success) {
    const properties = z.treeifyError(validatedFields.error).properties;

    return {
      errors: {
        wallet: properties?.walletId,
        category: properties?.expenseCategoryId,
        amount: properties?.amount,
        notes: properties?.notes
      },
      message: 'Атрибуты формы заполнены некорректно'
    } satisfies State;
  }

  const newExpenseTransaction: CreateExpenseTransactionDto = validatedFields.data satisfies CreateExpenseTransactionDto;

  try {
    await transactionApiService.withdraw(newExpenseTransaction);
  } catch (error) {
    return {
      message: 'Произошла ошибка: Кошелек не был создан'
    } satisfies State;
  }

  revalidatePath("/transactions");
  redirect("/transactions");
}
