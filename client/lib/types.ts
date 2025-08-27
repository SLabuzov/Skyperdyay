import { UUID } from "crypto";

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
