import { walletApiService } from "@/services/wallet-api-service";
import { categoryApiService } from "@/services/category-api-service";
import NewIncomeTransactionForm from "@/components/transaction/new-income-transaction-form";

export default async function TopUpPage() {
  const wallets = await walletApiService.getWallets();
  const categories = await categoryApiService.getCategories();
  const incomeCategories = categories.filter(item => item.categoryType === 'INCOME');

  return (
    <div className="flex flex-col flex-1 gap-4 px-8">
      <div className="flex items-center justify-between h-10">
        <h1 className="text-base font-bold text-gray-600">Добавить новую запись о доходах</h1>
      </div>
      <div>
        <NewIncomeTransactionForm wallets={ wallets } incomeCategories={ incomeCategories }/>
      </div>
    </div>
  );
}