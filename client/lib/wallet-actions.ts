'use server';

import { revalidatePath } from "next/cache";
import { redirect } from "next/navigation";
import { z } from "zod";
import { CreateWalletDto } from "@/lib/types";
import { walletApiService } from "@/services/wallet-api-service";

const CreateWalletFormSchema = z.object({
  walletName: z.string(),
  currencyCode: z.string(),
  balance: z.coerce.number()
});

export type State = {
  errors?: {
    walletName?: {
      errors?: string[] | null;
    },
    currencyCode?: {
      errors?: string[] | null;
    },
    balance?: {
      errors?: string[] | null;
    }
  };
  message?: string | null;
}

export async function createWallet(prevState: State, formData: FormData) {
  const validatedFields = CreateWalletFormSchema.safeParse({
    walletName: formData.get('walletName'),
    balance: formData.get('balance'),
    currencyCode: formData.get('currency')
  });

  if (!validatedFields.success) {
    const properties = z.treeifyError(validatedFields.error).properties;

    return {
      errors: {
        walletName: properties?.walletName,
        currencyCode: properties?.currencyCode,
        balance: properties?.balance
      },
      message: 'Атрибуты формы заполнены некорректно'
    } satisfies State;
  }

  const newWallet: CreateWalletDto = validatedFields.data satisfies CreateWalletDto;

  try {
    await walletApiService.registerWallet(newWallet);
  } catch (error) {
    return {
      message: 'Произошла ошибка: Кошелек не был создан'
    } satisfies State;
  }

  revalidatePath("/wallets");
  redirect("/wallets");
}
