import React from 'react';
import Link from 'next/link';
import { Plus } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { categoryApiService } from '@/services/category-api-service';
import CategoryInfoCard from '@/components/category/category-info-card';

export default async function CategoriesPage() {
  const categories = await categoryApiService.getCategories();
  const incomeCategories = categories.filter(item => item.categoryType === 'INCOME');
  const expenseCategories = categories.filter(item => item.categoryType === 'EXPENSE');

  return (
    <div className="flex flex-col flex-1 gap-4 px-8 overflow-hidden">
      <div className="flex items-center justify-between h-10 min-h-10">
        <h1 className="text-base font-bold text-gray-600">Мои финансовые категории</h1>
        <Button asChild variant="link" className="cursor-pointer text-primary">
          <Link href={ '/categories/new' }>
            <Plus/>
            Добавить новую финансовую категорию
          </Link>
        </Button>
      </div>
      <div className="grid grid-cols-2 gap-8 overflow-y-auto">
        <div className="flex flex-col gap-4">
          { incomeCategories.map(category => (
            <CategoryInfoCard key={ category.categoryId } categoryInfo={ category }/>
          )) }
        </div>
        <div className="flex flex-col gap-4">
          { expenseCategories.map(category => (
            <CategoryInfoCard key={ category.categoryId } categoryInfo={ category }/>
          )) }
        </div>
        <div></div>
      </div>
    </div>
  );
}
