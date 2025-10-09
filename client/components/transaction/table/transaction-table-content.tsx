import React from 'react';
import { TransactionInfo } from '@/lib/types';
import { CategoryIcon } from '@/components/category/category-icon';
import { cn } from '@/lib/utils';
import { format } from 'date-fns';
import { ru } from 'date-fns/locale';

interface Props {
  transactions: TransactionInfo[]
}

export default function TransactionTableContent({ transactions }: Props) {

  return (
    <div className="w-full overflow-auto mb-2">
      { transactions.map((transaction) => {
        const amountColorStyle = transaction.category.categoryType === 'INCOME' ? 'text-success' : 'text-error';

        return (
          <div key={ transaction.transactionId }
               className="flex items-center justify-between py-[18px] px-5 text-xs text-weak font-medium border-b-1 border-b-stroke-weak">
            <div className="w-[300px] max-w-[300px] flex items-center gap-2">
              <div
                className="flex w-8 h-8 bg-primary-weak rounded-full items-center justify-center text-primary">
                <CategoryIcon name={ transaction.category.categoryIcon } size={ 16 }/>
              </div>
              <div>
                <p className="text-strong text-xs">{ transaction.category.categoryName }</p>
                <p className="text-weak text-[10px]">{ transaction.wallet.walletName }</p>
              </div>
            </div>
            <div className={ cn("w-[100px] max-w-[100px] flex items-baseline justify-end gap-0.5", amountColorStyle) }>
              <p className="text-sm">
                { new Intl.NumberFormat("ru-RU").format(transaction.amount) }
              </p>
              <p className="text-xs">
                { transaction.wallet.walletCurrency.code }
              </p>
            </div>
            <div className="w-[180px] max-w-[180px]">
              { format(transaction.transactionDate, "PPP", { locale: ru }) }
            </div>
            <div className="w-[300px] max-w-[300px] text-weak font-medium text-xs">{ transaction.notes }</div>
            <div className="w-[30px] max-w-[30px]">...</div>
          </div>
        )
      }) }
    </div>
  );
}

