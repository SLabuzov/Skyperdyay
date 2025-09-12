import { WalletInfo } from "@/lib/types";
import React from "react";
import Link from "next/link";

interface Props {
  walletInfo: WalletInfo
}

export default function WalletInfoCard({ walletInfo }: Props) {

  return (
    <Link href={ `/wallets/${ walletInfo.walletId }` }
          className="flex items-center border-1 border-[#e5e6e6] rounded-xl p-4 gap-4 hover:bg-primary-weak">
      <div className="flex w-14 h-14 bg-primary-weak rounded-full items-center justify-center">
        <p className="text-2xl leading-none font-bold text-primary">{ walletInfo.walletCurrency.symbol }</p>
      </div>
      <div className="flex flex-col justify-between m-1">
        <p className="text-sm font-medium text-weak">{ walletInfo.walletName }</p>
        <div className="flex gap-0.5 items-baseline font-bold text-strong">
          <p className="text-2xl">{ new Intl.NumberFormat("ru-RU").format(walletInfo.walletBalance) }</p>
          <p className="text-sm">{ walletInfo.walletCurrency.code }</p>
        </div>
      </div>
    </Link>
  );
}
