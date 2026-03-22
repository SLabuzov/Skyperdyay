'use client';

import { Cell, Pie, PieChart, ResponsiveContainer } from 'recharts';
import { CategoryBreakdownItem, CategoryType } from '@/lib/types';

// Цветовая палитра для отображения распределения по категориям
const CATEGORY_COLORS = [
  '#1e4841',
  '#bbf49c',
  '#ecf4e9',
  '#e6e7f0',
  '#8c92af'
];

interface CategoriesBreakdownChartProps {
  categoryType: CategoryType;
  categories: CategoryBreakdownItem[];
  totalAmount: number;
  currencyCode: string;
}

const formatCurrency = (value: number, currencyCode: string = 'USD'): string => {
  return new Intl.NumberFormat('ru-RU', {
    style: 'currency',
    currency: currencyCode,
    minimumFractionDigits: 0,
    maximumFractionDigits: 0,
  }).format(value);
};


export function CategoriesBreakdownChart({
                                           categoryType,
                                           categories = [],
                                           totalAmount = 0,
                                           currencyCode = 'BYN'
                                         }: CategoriesBreakdownChartProps) {

  const chartData = categories
    .sort(((a, b) => b.totalAmount - a.totalAmount))
    .slice(0, 5).map((category, index) => ({
      name: category.categoryName,
      percentage: Math.round(category.percent),
      amount: category.totalAmount,
      color: CATEGORY_COLORS[index % CATEGORY_COLORS.length]
    }));

  const displayTitle = categoryType === 'INCOME' ? 'Категории доходов' : 'Категории расходов';
  const centerLabelTitle = categoryType === 'INCOME' ? 'Всего доходов' : 'Всего расходов';

  return (
    <div className="w-full p-4 border border-stroke-weak rounded-md">
      {/* Header */ }
      <div className="flex justify-between items-center mb-6">
        <h2 className="text-strong text-sm font-medium">{ displayTitle }</h2>
      </div>

      {/* Content: Chart + Legend */ }
      <div className="flex flex-col lg:flex-row items-center gap-8">
        {/* Donut Chart */ }
        <div className="relative w-56 h-56 flex-shrink-0">
          { chartData.length > 0 ? (
            <>
              <ResponsiveContainer width="100%" height="100%">
                <PieChart>
                  <Pie
                    data={ chartData }
                    cx="50%"
                    cy="50%"
                    innerRadius={ 75 }
                    outerRadius={ 95 }
                    paddingAngle={ 2 }
                    dataKey="amount"
                    stroke="none"
                  >
                    { chartData.map((entry, index) => (
                      <Cell key={ `cell-${ index }` } fill={ entry.color }/>
                    )) }
                  </Pie>
                </PieChart>
              </ResponsiveContainer>
              <CenterLabel title={ centerLabelTitle } totalAmount={ totalAmount }/>
            </>
          ) : (
            <div className="w-full h-full flex items-center justify-center">
              <p className="text-gray-400 text-sm">Нет данных</p>
            </div>
          ) }
        </div>

        {/* Categories List */ }
        <div className="flex-1 w-full">
          { chartData.length > 0 ? (
            <div className="space-y-3">
              { chartData.map((category, index) => (
                <div key={ index } className="flex items-center justify-between">
                  <div className="flex items-center gap-3">
                    {/* Percentage Badge */ }
                    <span
                      className="inline-flex items-center justify-center px-2.5 py-1 rounded-lg text-sm font-semibold min-w-[48px]"
                      style={ {
                        backgroundColor: category.color,
                        color:
                          category.color === '#1e4841'
                            ? '#ffffff'
                            : category.color === '#8c92af'
                              ? '#ffffff'
                              : '#1e4841',
                      } }
                    >
                      { category.percentage }%
                    </span>
                    {/* Category Name */ }
                    <span className="text-sm font-medium text-strong">
                      { category.name }
                    </span>
                  </div>
                  {/* Amount */ }
                  <span className="text-sm font-semibold text-strong">
                    { formatCurrency(category.amount, currencyCode) }
                  </span>
                </div>
              )) }
            </div>
          ) : (
            <div className="flex flex-col items-center justify-center py-8 text-gray-400">
              <p className="text-sm">Нет данных за выбранный период</p>
            </div>
          ) }
        </div>
      </div>
    </div>
  );
}


// Custom label for the center of the donut chart with adaptive font size
const CenterLabel = ({ title, totalAmount }: {
  title: string,
  totalAmount: number;
}) => {
  const formattedAmount = new Intl.NumberFormat("ru-RU").format(totalAmount);

  // Determine font size based on string length
  const getFontSizeClass = (text: string): string => {
    const length = text.length;
    if (length <= 8) return 'text-2xl';
    if (length <= 10) return 'text-xl';
    if (length <= 12) return 'text-lg';
    return 'text-base';
  };

  return (
    <div className="absolute inset-0 flex flex-col items-center justify-center pointer-events-none px-2">
      <p className="text-sm text-weak whitespace-nowrap">{ title }</p>
      <p
        className={ `text-primary text-2xl font-bold leading-tight ${ getFontSizeClass(formattedAmount) }` }>{ formattedAmount }</p>
    </div>
  );
};