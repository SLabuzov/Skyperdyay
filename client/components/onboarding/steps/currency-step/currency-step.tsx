"use client";

import React from 'react';
import { useOnboardingStore } from '@/lib/stores/onboarding-store';
import { Currency } from '@/lib/types';
import CurrencySelector from './currency-selector';
import StepperNavigation from '../../stepper-navigation';

interface Props {
  currencies: Currency[];
}

export default function CurrencyStep({ currencies }: Props) {
  const { profile, setCurrency, validateStep } = useOnboardingStore();

  return (
    <div className="flex flex-col gap-6">
      <div>
        <h2 className="text-2xl font-bold text-strong mb-2">Выберите основную валюту</h2>
        <p className="text-base text-weak">
          Это будет ваша основная валюта для отображения балансов и аналитики
        </p>
      </div>

      <CurrencySelector
        currencies={ currencies }
        value={ profile.currency }
        onChange={ setCurrency }
      />

      <StepperNavigation
        onNext={ () => {
        } }
        canProceed={ validateStep(3) }
        showBack={ true }
      />
    </div>
  );
}
