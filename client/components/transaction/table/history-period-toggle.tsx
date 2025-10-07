"use client";

import { HistoryPeriod } from '@/lib/types';
import { useRouter } from 'next/navigation';
import { ToggleGroup, ToggleGroupItem } from '@/components/ui/toggle-group';

interface Props {
  currentValue: HistoryPeriod;
}

export default function HistoryPeriodToggle({ currentValue }: Props) {
  const router = useRouter();

  return (
    <div>
      <ToggleGroup
        type="single"
        defaultValue={ currentValue }
        onValueChange={ (period) => {
          const query = period ? `?historyPeriod=${ period }` : '';
          router.push(`/transactions${ query }`);
        } }
        variant="outline"
        className="w-[400px]"
      >
        <ToggleGroupItem value="THIS_YEAR" className="h-8 px-2.5 text-xs">
          Текущий год
        </ToggleGroupItem>
        <ToggleGroupItem value="PREV_MONTH" className="h-8 px-2.5 text-xs">
          Прошлый месяц
        </ToggleGroupItem>
        <ToggleGroupItem value="THIS_MONTH" className="h-8 px-2.5 text-xs">
          Текущий месяц
        </ToggleGroupItem>
      </ToggleGroup>
    </div>

  );
}
