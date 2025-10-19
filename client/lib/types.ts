import { UUID } from "crypto";
import { IconName } from '@/lib/icon-map';

export interface Currency {
  code: string
  name: string
  symbol: string
}

export interface CreateWalletDto {
  walletName: string
  currencyCode: string
  balance: number
}

export interface WalletInfo {
  walletId: UUID
  walletName: string
  walletBalance: number
  walletCurrency: Currency
}

export type CategoryType = 'INCOME' | 'EXPENSE';

export interface CreateCategoryDto {
  name: string
  type: CategoryType
  icon: string
}

export interface CategoryInfo {
  categoryId: UUID
  categoryName: string
  categoryType: CategoryType
  categoryIcon: IconName
}

export interface CreateExpenseTransactionDto {
  walletId: UUID
  expenseCategoryId: UUID
  amount: number
  transactionDate: string
  notes: string | null | undefined
}

export interface CreateIncomeTransactionDto {
  walletId: UUID
  incomeCategoryId: UUID
  amount: number
  transactionDate: string
  notes: string | null | undefined
}

export interface TransactionInfo {
  transactionId: UUID
  amount: number
  transactionDate: string
  notes: string | null | undefined
  wallet: WalletInfo
  category: CategoryInfo
}

export type HistoryPeriod = "THIS_MONTH" | "PREV_MONTH" | "THIS_YEAR";
export type NullableHistoryPeriod = HistoryPeriod | null;

export interface TransactionHistoryParams {
  startPeriod: string
  endPeriod: string
}


export interface CashFlowInfo {
  month: string
  currencyCode: string
  expenseAmount: number
  incomeAmount: number
}

export interface FinanceScoreResult {
  financeQuality: "Отличное" | "Хорошее" | "Удовлетворительное"
  savingPercent: number
}