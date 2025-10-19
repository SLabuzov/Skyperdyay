import { FinanceScoreResult } from '@/lib/types';
import React from 'react';

interface Props {
  data: FinanceScoreResult
}

export default function FinanceScoreSection({ data }: Props) {
  return (
    <div className="flex flex-col border-1 border-stroke-weak bg-primary-weak rounded-md p-4 gap-4">
      <div className="flex items-center justify-between h-8">
        <p className="text-base font-bold text-strong">Финансовый результат</p>
      </div>
      <div className="flex flex-row items-center justify-between">
        <div>
          <p className="text-xs text-strong">Финансовое здоровье</p>
          <p className="text-2xl font-bold text-primary">{ data.financeQuality }</p>
        </div>
        <div className="flex items-center gap-6 text-xs font-semibold text-strong">

        </div>
      </div>
    </div>
  );
}
