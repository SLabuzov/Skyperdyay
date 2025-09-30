import React from 'react';
import Link from 'next/link';
import {transactionApiService} from '@/services/transaction-api-service';
import TransactionTable from '@/components/transaction/table/transaction-table';


export default async function TransactionsPage() {

  const history = await transactionApiService.history('2025-07-01', '2025-07-31');

  return (
    <div className="flex flex-col flex-1 gap-4 px-8">
      <div className="flex items-center justify-between h-10">
        <h1 className="text-base font-bold text-gray-600">Детальная информация по платежам</h1>
        <div className="flex items-center gap-2">
          <Link href={ "/transactions/top-up" }
                className="py-2 px-4 bg-fill-success-weak rounded-sm border-1 border-stroke-success-weak">
            <p className="text-sm font-semibold text-success">Записать доход</p>
          </Link>
          <Link href={ "/transactions/withdraw" }
                className="py-2 px-4 bg-fill-error-weak rounded-sm border-1 border-stroke-error-weak">
            <p className="text-sm font-semibold text-error">Записать расход</p>
          </Link>
        </div>
      </div>
      <div>
        <TransactionTable transactions={history}/>
      </div>
    </div>
  );
}
