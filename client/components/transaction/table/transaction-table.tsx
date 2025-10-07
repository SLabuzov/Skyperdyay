import React from 'react';
import HistoryPeriodToggle from '@/components/transaction/table/history-period-toggle';
import TransactionTableContent from '@/components/transaction/table/transaction-table-content';
import TransactionTableHeader from '@/components/transaction/table/transaction-table-header';
import { HistoryPeriod, TransactionInfo } from '@/lib/types';

interface Props {
  transactions: TransactionInfo[],
  historyPeriod: HistoryPeriod
}

export default function TransactionTable({ transactions, historyPeriod }: Props) {
  return (
    <div className="flex flex-col overflow-auto">
      <HistoryPeriodToggle currentValue={ historyPeriod }/>
      <TransactionTableHeader/>
      <TransactionTableContent transactions={ transactions }/>
    </div>
  );
}