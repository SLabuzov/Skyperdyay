"use client";

import React from 'react';
import { useOnboardingStore } from '@/lib/stores/onboarding-store';
import { PREDEFINED_INCOME_CATEGORIES, predefinedCategoryToDto } from '@/lib/predefined-categories';
import CategoryCard from './category-card';
import StepperNavigation from '../../stepper-navigation';

export default function IncomeCategoriesStep() {
  const { incomeCategories, setIncomeCategories, validateStep } = useOnboardingStore();

  const toggleCategory = (category: typeof PREDEFINED_INCOME_CATEGORIES[0]) => {
    const categoryDto = predefinedCategoryToDto(category);
    const isSelected = incomeCategories.some(
      c => c.name === category.name && c.type === category.type
    );

    if (isSelected) {
      setIncomeCategories(incomeCategories.filter(
        c => !(c.name === category.name && c.type === category.type)
      ));
    } else {
      setIncomeCategories([...incomeCategories, categoryDto]);
    }
  };

  return (
    <div className="flex flex-col gap-6">
      <div>
        <h2 className="text-2xl font-bold text-strong mb-2">Выберите категории доходов</h2>
        <p className="text-base text-weak">
          Выберите категории, которые вы будете использовать для учета доходов
        </p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        { PREDEFINED_INCOME_CATEGORIES.map((category) => {
          const isSelected = incomeCategories.some(
            c => c.name === category.name && c.type === category.type
          );
          return (
            <CategoryCard
              key={ `${ category.type }-${ category.name }` }
              category={ category }
              isSelected={ isSelected }
              onToggle={ () => toggleCategory(category) }
            />
          );
        }) }
      </div>

      <StepperNavigation
        onNext={ () => {
        } }
        canProceed={ validateStep(4) }
        showBack={ true }
      />
    </div>
  );
}
