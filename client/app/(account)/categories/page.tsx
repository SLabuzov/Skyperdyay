import React from 'react';
import Link from 'next/link';
import { Plus } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { categoryApiService } from '@/services/category-api-service';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { CategoryInfo } from '@/lib/types';
import CategoryInfoCard from '@/components/category/category-info-card';

export default async function CategoriesPage() {
  const categories = await categoryApiService.getCategories();

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
      <div className="grid grid-cols-2 gap-4 overflow-y-auto">
        <Tabs defaultValue="ALL" className="mb-8">
          <TabsList>
            <TabsTrigger value="ALL">Все категории</TabsTrigger>
            <TabsTrigger value="INCOME">Доходные категории</TabsTrigger>
            <TabsTrigger value="EXPENSE">Расходные категории</TabsTrigger>
          </TabsList>
          <TabsContent value="ALL" className="mt-6">
            <TabContent categories={ categories }
            />
          </TabsContent>
          <TabsContent value="INCOME" className="mt-6">
            <TabContent
              categories={ categories.filter(item => item.categoryType === 'INCOME') }
            />
          </TabsContent>
          <TabsContent value="EXPENSE" className="mt-6">
            <TabContent
              categories={ categories.filter(item => item.categoryType === 'EXPENSE') }
            />
          </TabsContent>
        </Tabs>
      </div>
    </div>
  );
}

function TabContent({ categories }: { categories: CategoryInfo[] }) {

  return (
    <div className="flex flex-col gap-4">
      { categories.map(category => (
        <CategoryInfoCard key={ category.categoryId } categoryInfo={ category }/>
      )) }
    </div>
  )
}