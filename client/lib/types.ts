import { UUID } from "crypto";
import { IconName } from '@/lib/icon-map';

export interface Currency {
  code: string
  name: string
  symbol: string
}

export interface Country {
  code: string
  name: string
  defaultCurrency: Currency
  defaultTimezone: Timezone
}

export interface Timezone {
  code: string
  name: string
}

export interface SubmitOnboardingDto {
  profile: CreateProfileDto
  incomeCategories: CreateCategoryDto[]
  expenseCategories: CreateCategoryDto[]
  wallet: CreateWalletDto
}

export interface CreateProfileDto {
  country: string
  timezone: string
  mainCurrencyCode: string
  acceptedTerms: boolean
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

export interface BalancePeriodMetrics {
  periodInterval: number
  incomeTotal: number
  expenseTotal: number
  netIncomeTotal: number
  incomeDiff: number
  expenseDiff: number
  netIncomeDiff: number
  incomePercentage: number
  expensePercentage: number
  netIncomePercentage: number
}

export interface FinancialReport {
  isMainCurrency: boolean
  currencyCode: string
  availableBalance: number
  balanceMetrics: BalancePeriodMetrics
}

export interface AnalyticsSummary {
  financialReports: FinancialReport[]
}

export interface CreateTransferDto {
  sourceWalletId: UUID
  targetWalletId: UUID
  sourceAmount: number
  targetAmount: number
  transferDate: string
  notes: string | null | undefined
}

export interface OnboardingStatusInfo {
  status: 'COMPLETED' | 'UNCOMPLETED'
}

export interface OnboardingFormData {
  country: string
  currency: string
  timezone: string
  incomeCategory: string
  expenseCategory: string
  acceptedTerms: false
}
