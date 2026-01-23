import { CategoryType, CreateCategoryDto } from '@/lib/types';
import { IconName } from '@/lib/icon-map';

export interface PredefinedCategory {
  name: string;
  type: CategoryType;
  icon: IconName;
}

export const PREDEFINED_INCOME_CATEGORIES: PredefinedCategory[] = [
  { name: 'Зарплата', type: 'INCOME', icon: 'Briefcase' },
  { name: 'Премия', type: 'INCOME', icon: 'Star' },
  { name: 'Подработка', type: 'INCOME', icon: 'BanknoteArrowUp' },
  { name: 'Инвестиции', type: 'INCOME', icon: 'TrendingUp' },
  { name: 'Подарки', type: 'INCOME', icon: 'Gift' },
  { name: 'Возврат долга', type: 'INCOME', icon: 'ArrowLeftCircle' },
  { name: 'Прочее', type: 'INCOME', icon: 'PlusCircle' },
];

export const PREDEFINED_EXPENSE_CATEGORIES: PredefinedCategory[] = [
  { name: 'Продукты', type: 'EXPENSE', icon: 'ShoppingCart' },
  { name: 'Кафе и рестораны', type: 'EXPENSE', icon: 'Utensils' },
  { name: 'Транспорт', type: 'EXPENSE', icon: 'Car' },
  { name: 'Общественный транспорт', type: 'EXPENSE', icon: 'Bus' },
  { name: 'Топливо', type: 'EXPENSE', icon: 'Fuel' },
  { name: 'Жилье', type: 'EXPENSE', icon: 'Home' },
  { name: 'Коммунальные услуги', type: 'EXPENSE', icon: 'Wifi' },
  { name: 'Связь', type: 'EXPENSE', icon: 'Smartphone' },
  { name: 'Одежда', type: 'EXPENSE', icon: 'Shirt' },
  { name: 'Здоровье', type: 'EXPENSE', icon: 'Heart' },
  { name: 'Развлечения', type: 'EXPENSE', icon: 'PlayCircle' },
  { name: 'Кино', type: 'EXPENSE', icon: 'Film' },
  { name: 'Музыка', type: 'EXPENSE', icon: 'Music' },
  { name: 'Игры', type: 'EXPENSE', icon: 'Gamepad2' },
  { name: 'Путешествия', type: 'EXPENSE', icon: 'Plane' },
  { name: 'Образование', type: 'EXPENSE', icon: 'GraduationCap' },
  { name: 'Книги', type: 'EXPENSE', icon: 'BookOpen' },
  { name: 'Красота', type: 'EXPENSE', icon: 'Scissors' },
  { name: 'Питомцы', type: 'EXPENSE', icon: 'PawPrint' },
  { name: 'Дети', type: 'EXPENSE', icon: 'Baby' },
  { name: 'Алкоголь', type: 'EXPENSE', icon: 'Beer' },
  { name: 'Сигареты', type: 'EXPENSE', icon: 'Cigarette' },
  { name: 'Прочее', type: 'EXPENSE', icon: 'PlusCircle' },
];

export function predefinedCategoryToDto(category: PredefinedCategory): CreateCategoryDto {
  return {
    name: category.name,
    type: category.type,
    icon: category.icon,
  };
}
