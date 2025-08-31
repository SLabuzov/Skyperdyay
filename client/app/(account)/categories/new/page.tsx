import React from "react";
import NewCategoryForm from '@/components/category/new-category-form';

export default function NewCategoryPage() {

  return (
    <div className="flex flex-col flex-1 gap-4 px-8">
      <div className="flex items-center justify-between h-10">
        <h1 className="text-base font-bold text-gray-600">Форма создания новой категории</h1>
      </div>
      <div className="flex flex-col text-primary w-sm">
        <NewCategoryForm/>
      </div>
    </div>
  );
}