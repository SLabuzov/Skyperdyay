import { AnalyticsSummary } from '@/lib/types';
import { apiClient } from '@/services/api-client';

class AnalyticsApiService {
  private readonly baseUrl: string = process.env.BACKEND_APP_API_ENDPOINT! + '/financial-analytics';


  async dashboard(): Promise<AnalyticsSummary> {
    return apiClient.get<AnalyticsSummary>(this.baseUrl + '/dashboard');
  }
}

export const analyticsApiService = new AnalyticsApiService();
