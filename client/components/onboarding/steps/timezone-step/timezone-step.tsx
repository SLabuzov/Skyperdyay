"use client";

import React from 'react';
import { useOnboardingStore } from '@/lib/stores/onboarding-store';
import { Timezone } from '@/lib/types';
import TimezoneSelector from './timezone-selector';
import StepperNavigation from '../../stepper-navigation';

interface Props {
  timezones: Timezone[];
}

export default function TimezoneStep({ timezones }: Props) {
  const { profile, setTimezone, validateStep } = useOnboardingStore();

  return (
    <div className="flex flex-col gap-6">
      <div>
        <h2 className="text-2xl font-bold text-strong mb-2">Настройте часовой пояс</h2>
        <p className="text-base text-weak">
          Мы определили часовой пояс по умолчанию на основе выбранной страны
        </p>
      </div>

      <TimezoneSelector
        timezones={ timezones }
        value={ profile.timezone }
        onChange={ setTimezone }
      />

      <StepperNavigation
        onNext={ () => {
        } }
        canProceed={ validateStep(2) }
        showBack={ true }
      />
    </div>
  );
}
