import { CashFlowInfo } from '@/lib/types';
import { apiClient } from '@/services/api-client';

class AnalyticsApiService {
  private readonly baseUrl: string = process.env.BACKEND_APP_API_ENDPOINT! + '/analytics';


  async cashFlow(startDate: string, endDate: string): Promise<CashFlowInfo[]> {
    const url = new URL(this.baseUrl + '/cash-flow');
    url.searchParams.append('startDate', String(startDate));
    url.searchParams.append('endDate', String(endDate));

    return apiClient.get<CashFlowInfo[]>(url.toString());
  }
}

export const analyticsApiService = new AnalyticsApiService();
