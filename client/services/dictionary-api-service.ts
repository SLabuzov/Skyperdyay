import { Country, Currency, Timezone } from '@/lib/types';
import { apiClient } from '@/services/api-client';

class DictionaryApiService {
  private readonly baseUrl: string = process.env.BACKEND_APP_API_ENDPOINT! + '/dictionaries';

  async getCurrencies(): Promise<Currency[]> {
    return apiClient.get<Currency[]>(this.baseUrl + '/currencies');
  }

  async getCountries(): Promise<Country[]> {
    return apiClient.get<Country[]>(this.baseUrl + '/countries');
  }

  async getTimezones(): Promise<Timezone[]> {
    return apiClient.get<Timezone[]>(this.baseUrl + '/timezones');
  }
}

export const dictionaryApiService = new DictionaryApiService();
