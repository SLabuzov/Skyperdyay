import { ArrowDown, ArrowUp, ArrowUpDown } from 'lucide-react';
import React from 'react';

interface Props {
  percentageChange: number
}

export default function PercentageBadge({ percentageChange }: Props) {

  if (percentageChange === 0) {
    return (
      <div
        className="flex items-center px-[5px] py-[2px] bg-fill-information-weak border border-stroke-information-weak text-information rounded-lg gap-0.5">
        <ArrowUpDown size={ 10 }/>
        <p className="text-xs font-semibold">+{ percentageChange } %</p>
      </div>
    );
  }

  if (percentageChange < 0) {
    return (
      <div
        className="flex items-center px-[5px] py-[2px] bg-fill-error-weak border border-stroke-error-weak text-error rounded-lg gap-0.5">
        <ArrowDown size={ 10 }/>
        <p className="text-xs font-semibold">{ percentageChange } %</p>
      </div>
    );
  }

  return (
    <div
      className="flex items-center px-[5px] py-[2px] bg-fill-success-weak border border-stroke-success-weak text-success rounded-lg gap-0.5">
      <ArrowUp size={ 10 }/>
      <p className="text-xs font-semibold">+{ percentageChange } %</p>
    </div>
  );
}