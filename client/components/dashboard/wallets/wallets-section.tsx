import { WalletInfo } from '@/lib/types';
import React from 'react';
import Link from 'next/link';
import { Plus } from 'lucide-react';
import { Button } from '@/components/ui/button';

interface Props {
  data: WalletInfo[]
}

export default function WalletsSection({ data }: Props) {
  return (
    <div className="flex flex-col border-1 border-stroke-weak rounded-md p-4 gap-4">
      <div className="flex items-center justify-between h-8">
        <p className="text-base font-bold text-strong">Баланс</p>
        <Button asChild variant="link" className="cursor-pointer text-xs text-primary gap-1">
          <Link href={ '/wallets/new' }>
            <Plus/>
            Добавить кошелек
          </Link>
        </Button>
      </div>
      { data.map((wallet) => (
        <div key={ wallet.walletId }
             className="bg-primary-weak flex border-1 border-stroke-weak rounded-md gap-4 overflow-hidden">
          <div className="flex items-center justify-center bg-primary w-15">
            <p className="text-xl text-secondary font-semibold">{ wallet.walletCurrency.symbol }</p>
          </div>
          <div className="flex flex-row flex-1 items-baseline justify-end p-4 gap-1">
            <p className="text-xl text-primary font-bold">
              { new Intl.NumberFormat("ru-RU").format(wallet.walletBalance) }
            </p>
            <p className="text-primary font-semibold">{ wallet.walletCurrency.code }</p>
          </div>
        </div>
      )) }
    </div>
  );
}
