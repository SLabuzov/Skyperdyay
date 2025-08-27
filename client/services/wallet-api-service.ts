import { CreateWalletDto, WalletInfo } from '@/lib/types';
import { apiClient } from '@/services/api-client';

class WalletApiService {
  private readonly baseUrl: string = process.env.BACKEND_APP_API_ENDPOINT! + '/wallets';

  async getWallets(): Promise<WalletInfo[]> {
    return apiClient.get<WalletInfo[]>(this.baseUrl);
  }

  async registerWallet(createWalletDto: CreateWalletDto): Promise<void> {
    return apiClient.post<CreateWalletDto>(this.baseUrl, createWalletDto);
  }
}

export const walletApiService = new WalletApiService();