import React from 'react';
import Link from "next/link";
import TransactionTable from '@/components/transaction/table/transaction-table';
import { transactionApiService } from '@/services/transaction-api-service';
import { HistoryPeriod, NullableHistoryPeriod } from '@/lib/types';
import { constructPeriod } from '@/lib/history-period-utils';

interface PageParams {
  searchParams: Promise<{ historyPeriod: NullableHistoryPeriod }>
}

export default async function TransactionsPage({ searchParams }: PageParams) {

  const params = await searchParams;
  const historyPeriod: HistoryPeriod = params.historyPeriod || 'THIS_MONTH';

  const { startPeriod, endPeriod } = constructPeriod(historyPeriod);
  const history = await transactionApiService.history(startPeriod, endPeriod);

  return (
    <div className="flex flex-col flex-1 gap-4 px-8 overflow-hidden">
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
      <TransactionTable transactions={ history } historyPeriod={ historyPeriod }/>
    </div>
  );
}