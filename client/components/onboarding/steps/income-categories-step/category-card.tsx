"use client";

import React from 'react';
import { Check } from 'lucide-react';
import { cn } from '@/lib/utils';
import { iconMap } from '@/lib/icon-map';
import { PredefinedCategory } from '@/lib/predefined-categories';

interface Props {
  category: PredefinedCategory;
  isSelected: boolean;
  onToggle: () => void;
}

export default function CategoryCard({ category, isSelected, onToggle }: Props) {
  const IconComponent = iconMap[category.icon];

  return (
    <button
      type="button"
      onClick={ onToggle }
      className={ cn(
        "flex items-center gap-3 p-4 rounded-lg border-2 transition-all text-left",
        "hover:border-primary hover:bg-primary-weak",
        isSelected
          ? "border-primary bg-primary-weak"
          : "border-stroke-weak bg-white"
      ) }
    >
      <div className="flex-shrink-0">
        { IconComponent && <IconComponent size={ 24 } className="text-strong"/> }
      </div>
      <div className="flex-1 min-w-0">
        <h3 className="text-base font-semibold text-strong truncate">{ category.name }</h3>
      </div>
      { isSelected && (
        <div className="flex-shrink-0">
          <Check size={ 20 } className="text-primary"/>
        </div>
      ) }
    </button>
  );
}
