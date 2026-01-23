"use client";

import React from 'react';
import { Check } from 'lucide-react';
import { cn } from '@/lib/utils';
import { Country } from '@/lib/types';

interface Props {
  country: Country;
  isSelected: boolean;
  onSelect: () => void;
}

export default function CountryCard({ country, isSelected, onSelect }: Props) {
  return (
    <button
      type="button"
      onClick={ onSelect }
      className={ cn(
        "flex items-start gap-4 p-4 rounded-lg border-2 transition-all text-left",
        "hover:border-primary hover:bg-primary-weak",
        isSelected
          ? "border-primary bg-primary-weak"
          : "border-stroke-weak bg-white"
      ) }
    >
      <div className="flex-1">
        <div className="flex items-center justify-between mb-1">
          <h3 className="text-lg font-semibold text-strong">{ country.name }</h3>
          { isSelected && <Check size={ 20 } className="text-primary"/> }
        </div>
        <div className="text-sm text-weak space-y-1">
          <p>Валюта: <span className="font-medium">{ country.defaultCurrency?.code }</span></p>
          <p>Часовой пояс: <span className="font-medium">{ country.defaultTimezone?.code }</span></p>
        </div>
      </div>
    </button>
  );
}
