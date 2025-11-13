import React from 'react';

interface Props {
  currencyCode: string
  availableBalance: number
}

export default function BalanceCard({ currencyCode, availableBalance }: Props) {
  return (
    <div
      className="flex flex-1 items-center justify-between gap-2 border border-stoke-weak rounded-md p-4 bg-primary-weak">
      <div className="flex flex-1 flex-col gap-2">
        <div className="flex gap-2 items-center">
          <p className="text-strong text-sm font-medium">Доступный баланс</p>
        </div>
        <div className="flex gap-0.5 items-baseline justify-end">
          <p className="text-primary text-2xl font-bold">{ new Intl.NumberFormat("ru-RU").format(availableBalance) }</p>
          <p className="text-primary text-lg font-bold">{ currencyCode }</p>
        </div>
      </div>
    </div>
  );
}
