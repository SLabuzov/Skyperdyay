import { CategoryInfo } from '@/lib/types';
import { CategoryIcon } from '@/components/category/category-icon';

interface Props {
  categoryInfo: CategoryInfo
}

export default function CategoryInfoCard({ categoryInfo }: Props) {

  if (categoryInfo.categoryType === 'EXPENSE') {
    return (
      <div className="flex items-center border border-[#E5E6E6] p-[14px] rounded-[16px] gap-[14px]">
        <div className="flex items-center justify-center w-9 h-9 rounded-full bg-[#FDCED1] text-[#F73541]">
          <CategoryIcon name={ categoryInfo.categoryIcon } size={ 20 }/>
        </div>
        <div className="flex flex-col">
          <span className="font-medium text-gray-600">{ categoryInfo.categoryName }</span>
          <span className="text-sm font-base text-gray-400">0% от общего расхода</span>
        </div>
      </div>
    );
  }

  return (
    <div className="flex items-center border border-[#E5E6E6] p-[14px] rounded-[16px] gap-[14px]">
      <div className="flex items-center justify-center w-9 h-9 rounded-full bg-[#ECF4E9] text-primary">
        <CategoryIcon name={ categoryInfo.categoryIcon } size={ 20 }/>
      </div>
      <div className="flex flex-col">
        <span className="font-medium text-gray-600">{ categoryInfo.categoryName }</span>
        <span className="text-sm font-base text-gray-400">0% от общего дохода</span>
      </div>
    </div>
  );
}