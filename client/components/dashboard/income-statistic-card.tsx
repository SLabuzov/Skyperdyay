import React from 'react';
import { BanknoteArrowUp } from 'lucide-react';
import PercentageBadge from '@/components/dashboard/percentage-badge';

interface Props {
  currencyCode: string
  interval: number
  incomeTotal: number
  incomeDiff: number
  incomePercentage: number
}

export default function IncomeStatisticCard({
                                              currencyCode,
                                              interval,
                                              incomeTotal,
                                              incomeDiff,
                                              incomePercentage
                                            }: Props) {


  return (
    <div className="flex flex-1 items-center justify-between gap-2 border border-stoke-weak rounded-md p-4">
      <div className="flex flex-col gap-2">
        <div className="flex gap-2 items-center">
          <div className="flex bg-primary-weak text-primary rounded-full p-2">
            <BanknoteArrowUp size={ 16 }/>
          </div>
          <p className="text-strong text-sm font-medium">Доход</p>
        </div>
        <div className="flex gap-0.5 items-baseline">
          <p className="text-primary text-2xl font-bold">{ new Intl.NumberFormat("ru-RU").format(incomeTotal) }</p>
          <p className="text-primary text-lg font-bold">{ currencyCode }</p>
        </div>
      </div>
      <div className="flex flex-col gap-2">
        <div className="flex items-center justify-end">
          <PercentageBadge percentageChange={ incomePercentage }/>
        </div>
        <div className="flex gap-0.5 items-baseline">
          <p className="text-primary text-xs font-bold">{ new Intl.NumberFormat("ru-RU").format(incomeDiff) }</p>
          <p className="text-strong text-xs font-normal">за { interval } дней</p>
        </div>
      </div>
    </div>
  );
}
