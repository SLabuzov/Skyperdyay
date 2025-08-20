import { Currency } from '@/lib/types';
import { apiClient } from '@/services/api-client';

class CurrencyApiService {
  private readonly baseUrl: string = process.env.BACKEND_APP_API_ENDPOINT!;

  async getCurrencies(): Promise<Currency[]> {
    return apiClient.get<Currency[]>(this.baseUrl + '/currencies');
  }
}

export const currencyApiService = new CurrencyApiService();