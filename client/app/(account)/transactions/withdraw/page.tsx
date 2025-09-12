import React from 'react';
import { categoryApiService } from '@/services/category-api-service';
import { walletApiService } from '@/services/wallet-api-service';
import NewExpenseTransactionForm from '@/components/transaction/new-expense-transaction-form';


export default async function WithdrawPage() {

  const wallets = await walletApiService.getWallets();
  const categories = await categoryApiService.getCategories();
  const expenseCategories = categories.filter(item => item.categoryType === 'EXPENSE');


  return (
    <div className="flex flex-col flex-1 gap-4 px-8">
      <div className="flex items-center justify-between h-10">
        <h1 className="text-base font-bold text-gray-600">Добавить новую запись о расходах</h1>
      </div>
      <div>
        <NewExpenseTransactionForm wallets={ wallets } expenseCategories={ expenseCategories }/>
      </div>
    </div>
  );
}
