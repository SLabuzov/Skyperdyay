import React from "react";
import { Button } from '@/components/ui/button';
import { Plus } from 'lucide-react';
import Link from 'next/link';

interface Props {
  children: React.ReactNode
}

export default function WalletsLayout({ children }: Readonly<Props>) {
  return (
    <div className="flex flex-1 px-7 py-5 gap-4">
      <div className="flex flex-col flex-1 bg-[#ecf4e9] rounded-xl gap-4 p-4">
        <div className="flex justify-between items-center">
          <p className="text-base font-bold text-[#242e2c]">Мои карты</p>
          <Button asChild variant="link" className="cursor-pointer text-[#1e4841]">
            <Link href={"/wallets/new"}>
              <Plus/>
              Добавить
            </Link>
          </Button>
        </div>
        <div className="flex flex-col gap-4">
          <div className="bg-[#fbfbfc] border-1 border-[#e5e6e6] rounded-xl p-4 flex gap-4 items-center">
            <div className="flex w-14 h-14 bg-[#ecf4e9] rounded-full items-center justify-center">
              <p className="text-2xl leading-none font-bold text-[#1e4841]">$</p>
            </div>
            <div className="flex flex-col justify-between m-1">
              <p className="text-sm font-medium text-[#6B7271]">Наличные деньги</p>
              <p className="text-2xl font-bold text-[#1E4841]">138,500</p>
            </div>
          </div>

          <div className="bg-[#1E4841] border-1 border-[#e5e6e6] rounded-xl p-4 flex gap-4 items-center">
            <div className="flex w-14 h-14 bg-[#ecf4e9] rounded-full items-center justify-center">
              <p className="text-2xl leading-none font-bold text-[#1e4841]">$</p>
            </div>
            <div className="flex flex-col justify-between m-1">
              <p className="text-sm font-medium text-[#ECF4E9]">Unpaid Invoices</p>
              <p className="text-2xl font-bold text-[#FBFBFC]">138,500</p>
            </div>
          </div>
        </div>
      </div>
      {/* Create wallet form */ }
      <div className="flex flex-3 rounded-xl border-1 p-4 gap-4">
        { children }
      </div>
    </div>
  );
}