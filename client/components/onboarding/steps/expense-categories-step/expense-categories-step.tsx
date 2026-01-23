"use client";

import React from 'react';
import { useOnboardingStore } from '@/lib/stores/onboarding-store';
import { PREDEFINED_EXPENSE_CATEGORIES, predefinedCategoryToDto } from '@/lib/predefined-categories';
import CategoryCard from './category-card';
import StepperNavigation from '../../stepper-navigation';

export default function ExpenseCategoriesStep() {
  const { expenseCategories, setExpenseCategories, validateStep } = useOnboardingStore();

  const toggleCategory = (category: typeof PREDEFINED_EXPENSE_CATEGORIES[0]) => {
    const categoryDto = predefinedCategoryToDto(category);
    const isSelected = expenseCategories.some(
      c => c.name === category.name && c.type === category.type
    );

    if (isSelected) {
      setExpenseCategories(expenseCategories.filter(
        c => !(c.name === category.name && c.type === category.type)
      ));
    } else {
      setExpenseCategories([...expenseCategories, categoryDto]);
    }
  };

  return (
    <div className="flex flex-col gap-6">
      <div>
        <h2 className="text-2xl font-bold text-strong mb-2">Выберите категории расходов</h2>
        <p className="text-base text-weak">
          Выберите категории, которые вы будете использовать для учета расходов
        </p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        { PREDEFINED_EXPENSE_CATEGORIES.map((category) => {
          const isSelected = expenseCategories.some(
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
        canProceed={ validateStep(5) }
        showBack={ true }
      />
    </div>
  );
}
