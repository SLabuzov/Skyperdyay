"use client";

import React from 'react';
import { Button } from '@/components/ui/button';
import { useOnboardingStore } from '@/lib/stores/onboarding-store';
import { ChevronLeft, Loader2 } from 'lucide-react';

interface Props {
  onNext: () => void;
  canProceed: boolean;
  showBack: boolean;
  submitLabel?: string;
  isSubmitting?: boolean;
}

export default function StepperNavigation({
                                            onNext,
                                            canProceed,
                                            showBack,
                                            submitLabel,
                                            isSubmitting = false,
                                          }: Props) {
  const { currentStep, setCurrentStep } = useOnboardingStore();

  const handleBack = () => {
    if (currentStep > 1) {
      setCurrentStep(currentStep - 1);
    }
  };

  const handleNext = () => {
    if (canProceed && !isSubmitting) {
      if (currentStep < 7) {
        setCurrentStep(currentStep + 1);
      } else {
        onNext();
      }
    }
  };

  const isLastStep = currentStep === 7;
  const buttonLabel = isLastStep
    ? submitLabel || 'Завершить настройку'
    : 'Далее';

  const isDisabled = Boolean(!canProceed || isSubmitting);
  const isBackDisabled = Boolean(isSubmitting);

  return (
    <div className="flex items-center justify-between pt-4 border-t border-stroke-weak">
      <div>
        { showBack && (
          <Button
            type="button"
            variant="outline"
            onClick={ handleBack }
            disabled={ isBackDisabled }
            className="flex items-center gap-2"
          >
            <ChevronLeft size={ 16 }/>
            Назад
          </Button>
        ) }
      </div>
      <Button
        type={ isLastStep ? "submit" : "button" }
        onClick={ isLastStep ? undefined : handleNext }
        disabled={ isDisabled }
        className="bg-primary text-primary-foreground flex items-center gap-2"
      >
        { isSubmitting && <Loader2 size={ 16 } className="animate-spin"/> }
        { buttonLabel }
      </Button>
    </div>
  );
}
