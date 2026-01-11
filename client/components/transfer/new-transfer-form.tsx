"use client";

import React, { useActionState, useEffect, useState } from 'react';
import Link from 'next/link';
import { ArrowRight, Asterisk, CalendarIcon, OctagonX } from 'lucide-react';
import { format } from 'date-fns';
import { ru } from 'date-fns/locale';
import { Label } from '@/components/ui/label';
import { Calendar } from '@/components/ui/calendar';
import { Button } from '@/components/ui/button';
import { Popover, PopoverContent, PopoverTrigger } from '@/components/ui/popover';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { Input } from '@/components/ui/input';
import { cn } from '@/lib/utils';
import { WalletInfo } from '@/lib/types';
import { createTransfer, State } from '@/lib/transfer-actions';

interface FormProps {
  wallets: WalletInfo[]
}

export default function NewTransferForm({ wallets }: FormProps) {
  const [transferDate, setTransferDate] = useState<Date>(new Date());
  const [sourceWalletId, setSourceWalletId] = useState<string>(wallets[0].walletId);
  const [targetWalletId, setTargetWalletId] = useState<string>(wallets[1].walletId);

  const initialState: State = { message: null };
  const [state, formAction] = useActionState(createTransfer, initialState);

  const sourceWallet = wallets.find(w => w.walletId === sourceWalletId);
  const targetWallet = wallets.find(w => w.walletId === targetWalletId);

  const availableSourceWallets = wallets;
  const availableTargetWallets = wallets.filter(w => w.walletId !== sourceWalletId);

  // Автоматически обновляем целевой кошелек при изменении исходного
  useEffect(() => {
    // Если целевой кошелек совпадает с исходным, меняем его
    if (sourceWalletId && targetWalletId === sourceWalletId) {
      // Находим первый доступный кошелек, который не является исходным
      const newTargetWallet = availableTargetWallets[0];
      setTargetWalletId(newTargetWallet.walletId);
    }
  }, [sourceWalletId, targetWalletId, availableTargetWallets, wallets]);

  return (
    <form action={ formAction } className="flex flex-col gap-4">
      { state.message &&
          <div className="flex gap-3 p-6 text-sm border-1 border-stroke-error-weak bg-fill-error-weak rounded-sm">
              <div className="flex items-center text-icon-error h-7">
                  <OctagonX/>
              </div>
              <div className="flex flex-col gap-1">
                  <h4 className="text-xl font-semibold text-strong">Ошибка</h4>
                  <p className="text-base font-normal text-weak">{ state.message }</p>
              </div>
          </div>
      }
      <div className="flex gap-4 items-end">

        {/* Исходный кошелек */ }
        <div className="w-sm space-y-2">
          <Label htmlFor="sourceWallet" className="flex">
            Из кошелька
            <Asterisk size={ 12 } className="text-red-600"/>
          </Label>
          <Select name="sourceWallet" required value={ sourceWalletId } onValueChange={ setSourceWalletId }>
            <SelectTrigger id="sourceWallet" className="w-full">
              <SelectValue placeholder="Выберите кошелек"/>
            </SelectTrigger>
            <SelectContent>
              { availableSourceWallets.map((wallet) => (
                <SelectItem key={ wallet.walletId } value={ wallet.walletId }>
                  <div className="flex w-10 h-10 bg-primary-weak rounded-full items-center justify-center">
                    <p className="text-lg leading-none text-primary">{ wallet.walletCurrency.symbol }</p>
                  </div>
                  <div className="flex flex-col justify-between m-1">
                    <p className="text-xs font-medium text-weak">{ wallet.walletName }</p>
                    <div className="flex gap-0.5 items-baseline font-bold text-strong">
                      <p className="text-md">{ new Intl.NumberFormat("ru-RU").format(wallet.walletBalance) }</p>
                      <p className="text-xs">{ wallet.walletCurrency.code }</p>
                    </div>
                  </div>
                </SelectItem>
              )) }
            </SelectContent>
          </Select>
        </div>

        <div className="flex items-center text-icon-primary mb-2 h-[62px]">
          <ArrowRight size={ 36 }/>
        </div>

        {/* Целевой кошелек */ }
        <div className="w-sm space-y-2">
          <Label htmlFor="targetWallet" className="flex">
            На кошелек
            <Asterisk size={ 12 } className="text-red-600"/>
          </Label>
          <Select name="targetWallet" required value={ targetWalletId } onValueChange={ setTargetWalletId }>
            <SelectTrigger id="targetWallet" className="w-full">
              <SelectValue placeholder="Выберите кошелек"/>
            </SelectTrigger>
            <SelectContent>
              { availableTargetWallets.map((wallet) => (
                <SelectItem key={ wallet.walletId } value={ wallet.walletId }>
                  <div className="flex w-10 h-10 bg-primary-weak rounded-full items-center justify-center">
                    <p className="text-lg leading-none text-primary">{ wallet.walletCurrency.symbol }</p>
                  </div>
                  <div className="flex flex-col justify-between m-1">
                    <p className="text-xs font-medium text-weak">{ wallet.walletName }</p>
                    <div className="flex gap-0.5 items-baseline font-bold text-strong">
                      <p className="text-md">{ new Intl.NumberFormat("ru-RU").format(wallet.walletBalance) }</p>
                      <p className="text-xs">{ wallet.walletCurrency.code }</p>
                    </div>
                  </div>
                </SelectItem>
              )) }
            </SelectContent>
          </Select>
        </div>
      </div>

      <div className="flex flex-col w-sm gap-4">
        {/* Дата перевода */ }
        <div className="space-y-2">
          <Label htmlFor="transferDate" className="flex">
            Дата перевода
            <Asterisk size={ 12 } className="text-red-600"/>
          </Label>
          <Popover>
            <PopoverTrigger asChild>
              <Button
                variant={ 'outline' }
                className={ cn("w-full justify-start text-left font-normal", !transferDate && "text-muted-foreground") }
                id="transferDate"
                type="button"
              >
                <CalendarIcon className="mr-2 h-4 w-4"/>
                { transferDate ? format(transferDate, "PPP", { locale: ru }) : "Установите дату перевода" }
              </Button>
            </PopoverTrigger>
            <PopoverContent className="w-auto p-0">
              <Calendar mode="single" locale={ ru } selected={ transferDate } onSelect={ setTransferDate }
                        required={ true }/>
            </PopoverContent>
          </Popover>
          <input type="hidden" name="transferDate"
                 value={ transferDate ? format(transferDate, "yyyy-MM-dd") : "" }/>
        </div>

        {/* Сумма списания */ }
        <div className="space-y-2">
          <Label htmlFor="sourceAmount" className="flex">
            Сумма к переводу
            { sourceWallet && <span className="ml-2 text-xs text-weak">({ sourceWallet.walletCurrency.code })</span> }
            <Asterisk size={ 12 } className="text-red-600"/>
          </Label>
          <Input
            id="sourceAmount"
            name="sourceAmount"
            required
            type="number"
            min="0.01"
            step="0.01"
            defaultValue="100"
          />
        </div>

        {/* Сумма зачисления */ }
        <div className="space-y-2">
          <Label htmlFor="targetAmount" className="flex">
            Сумма к зачислению
            { targetWallet && <span className="ml-2 text-xs text-weak">({ targetWallet.walletCurrency.code })</span> }
            <Asterisk size={ 12 } className="text-red-600"/>
          </Label>
          <Input
            id="targetAmount"
            name="targetAmount"
            required
            type="number"
            min="0.01"
            step="0.01"
            defaultValue="100"
          />
        </div>

        {/* Заметки */ }
        <div className="space-y-2">
          <Label htmlFor="notes" className="flex">
            Заметки
          </Label>
          <Input id="notes" name="notes"/>
        </div>

        {/* Экшены */ }
        <div className="space-y-2 flex justify-between">
          <Button variant="outline" type="button" asChild>
            <Link href={ "/transactions" }>Отменить</Link>
          </Button>
          <Button type="submit" className="cursor-pointer bg-primary">
            Выполнить перевод
          </Button>
        </div>
      </div>
    </form>
  );
}
