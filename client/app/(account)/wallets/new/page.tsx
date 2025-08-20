import NewWalletForm from '@/components/wallet/new-wallet-form';
import { currencyApiService } from '@/services/currency-api-service';

export default async function NewWalletPage() {

  const currencies = await currencyApiService.getCurrencies();

  return (
    <div className="flex flex-col flex-1 gap-4">
      <div>
        <h1 className="text-base font-bold text-[#1A1615]">Форма создания нового кошелька</h1>
      </div>
      <div className="flex flex-col flex-1 rounded-xl items-center justify-center bg-[#ecf4e9] text-[#6B7271]">
        <NewWalletForm currencies={ currencies }/>
      </div>
    </div>
  );
}