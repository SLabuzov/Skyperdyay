"use client";

import React from 'react';
import { useOnboardingStore } from '@/lib/stores/onboarding-store';
import { Country, Currency, Timezone } from '@/lib/types';
import CountryStep from './steps/country-step/country-step';
import TimezoneStep from './steps/timezone-step/timezone-step';
import CurrencyStep from './steps/currency-step/currency-step';
import IncomeCategoriesStep from './steps/income-categories-step/income-categories-step';
import ExpenseCategoriesStep from './steps/expense-categories-step/expense-categories-step';
import WalletStep from './steps/wallet-step/wallet-step';
import TermsStep from './steps/terms-step/terms-step';
import { Wallet } from "lucide-react";

interface Props {
  countries: Country[];
  timezones: Timezone[];
  currencies: Currency[];
}

export default function OnboardingStepper({ countries, timezones, currencies }: Props) {
  const { currentStep } = useOnboardingStore();

  const renderStep = () => {
    switch (currentStep) {
      case 1:
        return <CountryStep countries={ countries }/>;
      case 2:
        return <TimezoneStep timezones={ timezones }/>;
      case 3:
        return <CurrencyStep currencies={ currencies }/>;
      case 4:
        return <IncomeCategoriesStep/>;
      case 5:
        return <ExpenseCategoriesStep/>;
      case 6:
        return <WalletStep/>;
      case 7:
        return <TermsStep/>;
      default:
        return <CountryStep countries={ countries }/>;
    }
  };

  return (
    <div className="flex-1 flex flex-col min-w-xl max-w-lg w-full pt-10 gap-6">
      <div className="flex justify-start">
        <Wallet className="h-9 w-9 text-primary"/>
      </div>
      <div className="mb-8">
        { renderStep() }
      </div>
    </div>
  );
}
