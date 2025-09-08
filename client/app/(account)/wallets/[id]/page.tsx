import React from "react";
import Link from "next/link";
import { UUID } from "crypto";
import { walletApiService } from "@/services/wallet-api-service";
import { SquareMinus, SquarePlus } from 'lucide-react';

interface Props {
  id: UUID
}

export default async function WalletPage(props: { params: Promise<Props> }) {
  const params = await props.params;
  const id = params.id;

  const wallet = await walletApiService.getWallet(id);

  return (
    <div className="flex flex-col flex-1 gap-4 px-8">
      <div className="flex items-center justify-between h-10">
        <h1 className="text-base font-bold text-gray-600">Детальная информация по кошельку [{ wallet.walletName }]</h1>
      </div>
      <div className="flex gap-8">
        <div className="flex flex-col border-1 border-[#e5e6e6] rounded-xl p-4 gap-2 w-sm">
          <div className="flex-1 flex flex-col justify-between m-1">
            <p className="text-sm font-medium text-[#6B7271]">{ wallet.walletName }</p>
            <div className="flex gap-0.5 items-baseline font-bold text-primary">
              <p className="text-2xl">{ new Intl.NumberFormat("ru-RU").format(wallet.walletBalance) }</p>
              <p className="text-sm">{ wallet.walletCurrency.code }</p>
            </div>
          </div>
          <div className="flex items-center justify-between gap-2">
            <Link href={ `/wallets/${ id }/top-up` }
                  className="flex flex-col gap-1 items-center justify-center flex-1 py-2 bg-fill-success-weak rounded-xl border-1 border-stroke-success-weak">
              <SquarePlus className="text-icon-success"/>
              <p className="font-semibold text-success">Пополнить</p>
            </Link>
            <Link href={ `/wallets/${ id }/withdraw` }
                  className="flex flex-col gap-1 items-center justify-center flex-1 py-2 bg-fill-error-weak rounded-xl border-1 border-stroke-error-weak">
              <SquareMinus className="text-icon-error"/>
              <p className="font-semibold text-error">Оплатить</p>
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}
