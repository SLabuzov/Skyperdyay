import { CreateWalletDto, WalletInfo } from '@/lib/types';
import { apiClient } from '@/services/api-client';
import { UUID } from "crypto";

class WalletApiService {
  private readonly baseUrl: string = process.env.BACKEND_APP_API_ENDPOINT! + '/wallets';

  async getWallet(walletId: UUID): Promise<WalletInfo> {
    return apiClient.get<WalletInfo>(this.baseUrl + `/${ walletId }`);
  }

  async getWallets(): Promise<WalletInfo[]> {
    return apiClient.get<WalletInfo[]>(this.baseUrl);
  }

  async registerWallet(createWalletDto: CreateWalletDto): Promise<void> {
    return apiClient.post<CreateWalletDto>(this.baseUrl, createWalletDto);
  }
}

export const walletApiService = new WalletApiService();