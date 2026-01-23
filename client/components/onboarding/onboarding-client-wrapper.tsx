"use client";

import React, { useEffect, useState } from 'react';
import { useOnboardingStore } from '@/lib/stores/onboarding-store';
import OnboardingStepper from './onboarding-stepper';
import { Country, Currency, Timezone } from '@/lib/types';

interface Props {
  countries: Country[];
  timezones: Timezone[];
  currencies: Currency[];
}

export default function OnboardingClientWrapper({ countries, timezones, currencies }: Props) {
  const [isMounted, setIsMounted] = useState(false);
  const { reset } = useOnboardingStore();

  useEffect(() => {
    setIsMounted(true);
  }, []);

  useEffect(() => {
    const stored = localStorage.getItem('onboarding-storage');
    if (!stored) {
      reset();
    }
  }, [reset]);

  if (!isMounted) {
    return null;
  }

  return (
    <OnboardingStepper
      countries={ countries }
      timezones={ timezones }
      currencies={ currencies }
    />
  );
}
