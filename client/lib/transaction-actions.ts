'use server';

import { z } from "zod";
import { UUID } from "crypto";
import { CreateExpenseTransactionDto, CreateIncomeTransactionDto } from "@/lib/types";
import { transactionApiService } from "@/services/transaction-api-service";
import { revalidatePath } from "next/cache";
import { redirect } from "next/navigation";

const CreateExpenseTransactionFormSchema = z.object({
  walletId: z.uuid(),
  expenseCategoryId: z.uuid(),
  transactionDate: z.string().min(1, "Дата операции обязательна"),
  notes: z.string().nullish(),
  amount: z.coerce.number()
});

const CreateIncomeTransactionFormSchema = z.object({
  walletId: z.uuid(),
  incomeCategoryId: z.uuid(),
  transactionDate: z.string().min(1, "Дата операции обязательна"),
  notes: z.string().nullish(),
  amount: z.coerce.number()
});

export type State = {
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

  if (!validatedFields.success) {
    return {
      message: 'Атрибуты формы заполнены некорректно'
    } satisfies State;
  }

  const { walletId, expenseCategoryId, amount, transactionDate, notes } = validatedFields.data;

  const newExpenseTransaction: CreateExpenseTransactionDto = {
    walletId: walletId as UUID,
    expenseCategoryId: expenseCategoryId as UUID,
    amount: amount,
    transactionDate: transactionDate,
    notes: notes
  } satisfies CreateExpenseTransactionDto;

  try {
    await transactionApiService.withdraw(newExpenseTransaction);
  } catch (error) {
    return {
      message: 'Расходная операция не была добавлена'
    } satisfies State;
  }

  revalidatePath("/transactions");
  redirect("/transactions");
}

export async function createIncomeTransaction(prevState: State, formData: FormData) {
  const validatedFields = CreateIncomeTransactionFormSchema.safeParse({
    walletId: formData.get('wallet'),
    incomeCategoryId: formData.get('category'),
    transactionDate: formData.get('transactionDate'),
    amount: formData.get('amount'),
    notes: formData.get('notes')
  });

  if (!validatedFields.success) {
    return {
      message: 'Атрибуты формы заполнены некорректно'
    } satisfies State;
  }

  const { walletId, incomeCategoryId, amount, transactionDate, notes } = validatedFields.data;

  const newIncomeTransaction: CreateIncomeTransactionDto = {
    walletId: walletId as UUID,
    incomeCategoryId: incomeCategoryId as UUID,
    amount: amount,
    transactionDate: transactionDate,
    notes: notes
  } satisfies CreateIncomeTransactionDto;


  try {
    await transactionApiService.topUp(newIncomeTransaction);
  } catch (error) {
    return {
      message: 'Доходная операция не была добавлена'
    } satisfies State;
  }

  revalidatePath("/transactions");
  redirect("/transactions");
}
