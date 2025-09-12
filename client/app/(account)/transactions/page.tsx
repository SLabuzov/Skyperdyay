import React from 'react';
import Link from 'next/link';


export default async function TransactionsPage() {

  return (
    <div className="flex flex-col flex-1 gap-4 px-8">
      <div className="flex items-center justify-between h-10">
        <h1 className="text-base font-bold text-gray-600">Детальная информация по платежам</h1>
      </div>
      <div>
        <div className="flex items-center justify-between gap-2">
          <Link href={ "/transactions/top-up" }
                className="flex flex-col gap-1 items-center justify-center flex-1 py-2 bg-fill-success-weak rounded-sm border-1 border-stroke-success-weak">
            <p className="font-semibold text-success">Пополнить</p>
          </Link>
          <Link href={ "/transactions/withdraw" }
                className="flex flex-col gap-1 items-center justify-center flex-1 py-2 bg-fill-error-weak rounded-sm border-1 border-stroke-error-weak">
            <p className="font-semibold text-error">Оплатить</p>
          </Link>
        </div>
      </div>
    </div>
  );
}
