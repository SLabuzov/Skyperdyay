import { OnboardingStatusInfo, SubmitOnboardingDto } from '@/lib/types';
import { apiClient } from '@/services/api-client';

class OnboardingApiService {
  private readonly baseUrl: string = process.env.BACKEND_APP_API_ENDPOINT! + '/onboarding';

  async submitOnboarding(onboardingDto: SubmitOnboardingDto): Promise<void> {
    return apiClient.post<SubmitOnboardingDto>(this.baseUrl + '/submit', onboardingDto);
  }

  async onboardingStatus(): Promise<OnboardingStatusInfo> {
    return await apiClient.get<OnboardingStatusInfo>(this.baseUrl + '/status')
  }
}

export const onboardingApiService = new OnboardingApiService();
