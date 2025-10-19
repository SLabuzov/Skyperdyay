import { HistoryPeriod, TransactionHistoryParams } from '@/lib/types';
import { format } from 'date-fns';

function dateFormat(date: Date): string {
  return format(date, 'yyyy-MM-dd');
}

function constructCurrentMonth(currentDate: Date): TransactionHistoryParams {
  const year = currentDate.getFullYear();
  const month = currentDate.getMonth();

  return {
    startPeriod: dateFormat(new Date(year, month, 1)),
    endPeriod: dateFormat(new Date(year, month + 1, 0))
  } satisfies TransactionHistoryParams;
}

function constructPrevMonth(currentDate: Date): TransactionHistoryParams {
  const year = currentDate.getFullYear();
  const month = currentDate.getMonth();

  return {
    startPeriod: dateFormat(new Date(year, month - 1, 1)),
    endPeriod: dateFormat(new Date(year, month, 0))
  } satisfies TransactionHistoryParams;
}

function constructCurrentYear(currentDate: Date): TransactionHistoryParams {
  const year = currentDate.getFullYear();

  return {
    startPeriod: dateFormat(new Date(year, 0, 1)),
    endPeriod: dateFormat(new Date(year + 1, 0, 0))
  } satisfies TransactionHistoryParams;
}

export function constructPeriod(historyPeriod: HistoryPeriod): TransactionHistoryParams {

  const date = new Date();

  switch (historyPeriod) {
    case 'THIS_YEAR':
      return constructCurrentYear(date);
    case 'PREV_MONTH':
      return constructPrevMonth(date);
    default:
      return constructCurrentMonth(date);
  }
}