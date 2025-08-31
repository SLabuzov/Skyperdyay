'use client';

import { useState } from 'react';
import { iconMap, IconName } from '@/lib/icon-map';
import { Input } from '@/components/ui/input';


interface IconPickerProps {
  selectedIcon: IconName | null; // текущая выбранная иконка
  onSelect: (iconName: IconName) => void; // колбэк при выборе
  color?: string; // цвет для превью
  size?: number; // размер иконок в пикселях
}

// Количество иконок в строке (для сетки)
const GRID_COLUMNS = 6;

export function IconPicker({
                             selectedIcon,
                             onSelect,
                             color = '#737373FF',
                             size = 24,
                           }: IconPickerProps) {
  const [searchTerm, setSearchTerm] = useState('');

  // Фильтрация иконок по поисковому запросу
  const filteredIcons = Object.keys(iconMap).filter((iconName) =>
    iconName.toLowerCase().includes(searchTerm.toLowerCase())
  ) as IconName[];

  return (
    <div className="icon-picker">
      {/* Поле поиска */}
      <div className="mb-4">
        <Input
          type="text"
          placeholder="Поиск иконки... (например: Car, Home)"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="w-full px-4 py-2"
        />
      </div>

      {/* Сетка иконок */}
      <div
        className="grid gap-3"
        style={{
          gridTemplateColumns: `repeat(${GRID_COLUMNS}, 1fr)`,
        }}
      >
        {filteredIcons.length > 0 ? (
          filteredIcons.map((iconName) => {
            const IconComponent = iconMap[iconName];
            const isSelected = iconName === selectedIcon;

            return (
              <button
                type="button"
                key={iconName}
                onClick={() => onSelect(iconName)}
                className={`
                  p-3 rounded-lg border-2 transition-all duration-150 flex flex-col items-center justify-center cursor-pointer
                  ${isSelected ? 'border-[#1e4841] bg-[#BBF49C]' : 'border-transparent hover:border-gray-300 hover:bg-gray-50'}
                  focus:outline-none focus:ring-2 focus:ring-[#E0F2E0]
                `}
                aria-label={`Выбрать иконку ${iconName}`}
                title={iconName}
              >
                <IconComponent
                  size={size}
                  color={isSelected ? '#1e4841' : color}
                />
              </button>
            );
          })
        ) : (
          <div className="col-span-full py-6 text-center text-gray-500">
            Иконки не найдены. Попробуйте другое название.
          </div>
        )}
      </div>

      {/* Статистика */}
      <div className="mt-4 text-sm text-gray-500 text-center">
        Найдено: {filteredIcons.length} иконок
      </div>
    </div>
  );
}