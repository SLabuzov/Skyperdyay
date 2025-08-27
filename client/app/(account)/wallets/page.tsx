import React from "react";
import Link from "next/link";
import { Plus } from "lucide-react";
import { Button } from "@/components/ui/button";
import WalletInfoCard from "@/components/wallet/wallet-info-card";
import { walletApiService } from "@/services/wallet-api-service";

export default async function WalletsPage() {
  const wallets = await walletApiService.getWallets();

  return (
    <div className="flex flex-col flex-1 gap-4 px-8">
      <div className="flex items-center justify-between h-10">
        <h1 className="text-base font-bold text-gray-600">Мои кошельки</h1>
        <Button asChild variant="link" className="cursor-pointer text-primary">
          <Link href={ '/wallets/new' }>
            <Plus/>
            Добавить новый кошелёк
          </Link>
        </Button>
      </div>
      <div className="grid grid-cols-3 gap-4">
        { wallets.map(wallet => (
          <WalletInfoCard key={ wallet.walletId } walletInfo={ wallet }/>
        )) }
      </div>
    </div>
  );
}