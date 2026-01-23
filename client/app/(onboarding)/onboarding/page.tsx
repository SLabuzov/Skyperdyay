import { dictionaryApiService } from '@/services/dictionary-api-service';
import OnboardingClientWrapper from '@/components/onboarding/onboarding-client-wrapper';

export default async function OnboardingPage() {

  const [countries, timezones, currencies] = await Promise.all([
    dictionaryApiService.getCountries(),
    dictionaryApiService.getTimezones(),
    dictionaryApiService.getCurrencies(),
  ]);

  return (
    <OnboardingClientWrapper countries={ countries }
                             timezones={ timezones }
                             currencies={ currencies }/>
  );
}