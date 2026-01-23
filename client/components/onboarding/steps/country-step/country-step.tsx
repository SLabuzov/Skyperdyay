"use client";

import React, { useEffect } from 'react';
import { useOnboardingStore } from '@/lib/stores/onboarding-store';
import { Country } from '@/lib/types';
import CountryCard from './country-card';
import StepperNavigation from '../../stepper-navigation';

interface Props {
  countries: Country[];
}

export default function CountryStep({ countries }: Props) {
  const { profile, setCountry, validateStep, setTimezone, setCurrency } = useOnboardingStore();


  useEffect(() => {
    if (profile.country) {
      if (profile.country.defaultTimezone) {
        setTimezone(profile.country.defaultTimezone);
      }
      if (profile.country.defaultCurrency) {
        setCurrency(profile.country.defaultCurrency);
      }
    }
  }, [profile.country, setTimezone, setCurrency]);

  return (
    <div className="flex flex-col gap-6">
      <div>
        <h2 className="text-2xl font-bold text-strong mb-2">Выберите страну</h2>
        <p className="text-base text-weak">Это поможет нам настроить валюту и часовой пояс по умолчанию</p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        { countries.map((country) => (
          <CountryCard
            key={ country.code }
            country={ country }
            isSelected={ profile.country?.code === country.code }
            onSelect={ () => setCountry(country) }
          />
        )) }
      </div>

      <StepperNavigation
        onNext={ () => {
        } }
        canProceed={ validateStep(1) }
        showBack={ false }
      />
    </div>
  );
}
