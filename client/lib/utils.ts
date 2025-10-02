import { type ClassValue, clsx } from "clsx"
import { twMerge } from "tailwind-merge"
import { TransactionHistoryParams } from "@/lib/types";
import { format } from "date-fns/index";

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs))
}

export function currentMonthPeriod(): TransactionHistoryParams {
  const date = new Date();
  const startPeriod = format(new Date(date.getFullYear(), date.getMonth(), 1), 'yyyy-MM-dd');
  const endPeriod = format(new Date(date.getFullYear(), date.getMonth() + 1, 0), 'yyyy-MM-dd');

  return {
    startPeriod,
    endPeriod
  } satisfies TransactionHistoryParams;
}