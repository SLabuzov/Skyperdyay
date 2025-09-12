import { CategoryInfo } from '@/lib/types';
import { CategoryIcon } from '@/components/category/category-icon';
import { TrendingDown, TrendingUp } from 'lucide-react';

interface Props {
  categoryInfo: CategoryInfo
}

export default function CategoryInfoCard({ categoryInfo }: Props) {

  let descriptionCategoryType = 'доходная категория';
  let trendingIcon = <TrendingUp size={ 12 } className="text-icon-success"/>;

  if (categoryInfo.categoryType === 'EXPENSE') {
    descriptionCategoryType = 'расходная категория';
    trendingIcon = <TrendingDown size={ 12 } className="text-icon-error"/>;
  }

  return (
    <div className="flex items-center border border-[#E5E6E6] p-[14px] rounded-[16px] gap-[14px]">
      <div
        className="flex items-center justify-center w-9 h-9 rounded-full bg-primary-weak border-1 border-stroke-weak text-primary">
        <CategoryIcon name={ categoryInfo.categoryIcon } size={ 20 }/>
      </div>
      <div className="flex flex-col">
        <span className="flex items-center gap-1 text-sm text-strong font-medium">
          { categoryInfo.categoryName }
          { trendingIcon }
        </span>
        <span className="text-xs font-base text-weak">{ descriptionCategoryType }</span>
      </div>
    </div>
  );
}
