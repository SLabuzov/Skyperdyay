"use client";

import React, { useActionState, useRef, useState } from 'react';
import { useOnboardingStore } from '@/lib/stores/onboarding-store';
import { State, submitOnboarding } from '@/lib/onboarding-actions';
import TermsDocument from './terms-document';
import TermsCheckbox from './terms-checkbox';
import { OctagonX } from 'lucide-react';
import StepperNavigation from '@/components/onboarding/stepper-navigation';

export default function TermsStep() {
  const {
    profile,
    incomeCategories,
    expenseCategories,
    wallet,
    acceptedTerms,
    setAcceptedTerms,
    validateStep,
  } = useOnboardingStore();

  const [hasScrolledToBottom, setHasScrolledToBottom] = useState(false);
  const scrollContainerRef = useRef<HTMLDivElement>(null);

  const initialState: State = { message: null };
  const [state, formAction] = useActionState(submitOnboarding, initialState);

  const handleScroll = () => {
    if (scrollContainerRef.current) {
      const { scrollTop, scrollHeight, clientHeight } = scrollContainerRef.current;
      const isAtBottom = scrollTop + clientHeight >= scrollHeight - 10;
      setHasScrolledToBottom(isAtBottom);
    }
  };

  return (
    <form action={ formAction } className="flex flex-col gap-6">

      <input type="hidden" name="countryCode" value={ profile.country?.code || '' }/>
      <input type="hidden" name="timezoneCode" value={ profile.timezone?.code || '' }/>
      <input type="hidden" name="currencyCode" value={ profile.currency?.code || '' }/>
      <input type="hidden" name="incomeCategories" value={ JSON.stringify(incomeCategories) }/>
      <input type="hidden" name="expenseCategories" value={ JSON.stringify(expenseCategories) }/>
      <input type="hidden" name="wallet" value={ JSON.stringify(wallet) }/>
      <input type="hidden" name="acceptedTerms" value={ acceptedTerms ? 'true' : 'false' }/>

      <div>
        <h2 className="text-2xl font-bold text-strong mb-2">Условия использования</h2>
        <p className="text-base text-weak">
          Пожалуйста, прочитайте условия использования перед продолжением
        </p>
      </div>

      { state.message &&
          <div className="flex gap-3 p-6 text-sm border-1 border-stroke-error-weak bg-fill-error-weak rounded-sm">
              <div className="flex items-center text-icon-error h-7">
                  <OctagonX/>
              </div>
              <div className="flex flex-col gap-1">
                  <h4 className="text-xl font-semibold text-strong">Ошибка</h4>
                  <p className="text-base font-normal text-weak">{ state.message }</p>
              </div>
          </div>
      }

      <div
        ref={ scrollContainerRef }
        onScroll={ handleScroll }
        className="border border-stroke-weak rounded-lg p-4 max-h-[400px] overflow-y-auto"
      >
        <TermsDocument/>
      </div>

      <TermsCheckbox
        checked={ acceptedTerms }
        onChange={ setAcceptedTerms }
        disabled={ !hasScrolledToBottom }
      />

      <StepperNavigation
        onNext={ () => {
        } }
        canProceed={ validateStep(7) }
        showBack={ true }
      />

    </form>
  );
}
