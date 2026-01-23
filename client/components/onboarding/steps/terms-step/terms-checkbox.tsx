"use client";

import React from 'react';
import { Label } from '@/components/ui/label';
import { Asterisk, Check } from 'lucide-react';
import { cn } from '@/lib/utils';

interface Props {
  checked: boolean;
  onChange: (checked: boolean) => void;
  disabled?: boolean;
}

export default function TermsCheckbox({ checked, onChange, disabled }: Props) {
  return (
    <div className="flex items-start gap-3 p-4 border border-stroke-weak rounded-lg bg-fill-weak">
      <button
        type="button"
        onClick={ () => !disabled && onChange(!checked) }
        disabled={ disabled }
        className={ cn(
          "mt-1 flex h-4 w-4 items-center justify-center rounded border-2 transition-colors",
          checked
            ? "border-primary bg-primary text-primary-foreground"
            : "border-stroke-weak bg-white",
          disabled && "opacity-50 cursor-not-allowed",
          !disabled && "cursor-pointer hover:border-primary"
        ) }
      >
        { checked && <Check size={ 12 }/> }
      </button>
      <Label
        htmlFor="acceptTerms"
        className={ cn(
          "flex-1 text-sm cursor-pointer",
          disabled && "opacity-50 cursor-not-allowed",
          !disabled && "hover:text-strong"
        ) }
        onClick={ () => !disabled && onChange(!checked) }
      >
        <span className="flex items-center gap-0">
          Я прочитал и принимаю
          <Asterisk size={ 10 } className="text-red-600 ml-1"/>
        </span>
        <span className="text-xs text-weak block mt-1">
          { disabled && "Прокрутите документ до конца, чтобы принять условия" }
        </span>
      </Label>
    </div>
  );
}
