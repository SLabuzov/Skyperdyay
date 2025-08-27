import React from "react";
import NewWalletForm from '@/components/wallet/new-wallet-form';
import { currencyApiService } from '@/services/currency-api-service';

export default async function NewWalletPage() {
  const currencies = await currencyApiService.getCurrencies();

  return (
    <div className="flex flex-col flex-1 gap-4 px-8">
      <div className="flex items-center justify-between h-10">
        <h1 className="text-base font-bold text-gray-600">Форма создания нового кошелька</h1>
      </div>
      <div className="flex flex-col text-primary w-sm">
        <NewWalletForm currencies={ currencies }/>
      </div>
    </div>
  );
}