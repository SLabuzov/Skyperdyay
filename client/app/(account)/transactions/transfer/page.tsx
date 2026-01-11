import { walletApiService } from "@/services/wallet-api-service";
import NewTransferForm from "@/components/transfer/new-transfer-form";

export default async function TransferPage() {
  const wallets = await walletApiService.getWallets();

  return (
    <div className="flex flex-col flex-1 gap-4 px-8">
      <div className="flex items-center justify-between h-10">
        <h1 className="text-base font-bold text-gray-600">Выполнить перевод</h1>
      </div>
      <div>
        <NewTransferForm wallets={ wallets }/>
      </div>
    </div>
  );
}
