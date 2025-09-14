"use client";

import React, { useActionState, useState } from 'react';
import Link from 'next/link';
import { Asterisk, CalendarIcon, ChevronsRight, OctagonX } from 'lucide-react';
import { format } from 'date-fns';
import { ru } from 'date-fns/locale';
import { Label } from '@/components/ui/label';
import { Calendar } from '@/components/ui/calendar';
import { Button } from '@/components/ui/button';
import { Popover, PopoverContent, PopoverTrigger } from '@/components/ui/popover';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { CategoryIcon } from '@/components/category/category-icon';
import { Input } from '@/components/ui/input';
import { cn } from '@/lib/utils';
import { CategoryInfo, WalletInfo } from '@/lib/types';
import { createIncomeTransaction, State } from '@/lib/transaction-actions';

interface FormProps {
  wallets: WalletInfo[]
  incomeCategories: CategoryInfo[]
}

export default function NewIncomeTransactionForm({ wallets, incomeCategories }: FormProps) {

  const [transactionDate, setTransactionDate] = useState<Date>(new Date());
  const [currentWallet, setCurrentWallet] = useState<string>(wallets[0].walletId);
  const [currentCategory, setCurrentCategory] = useState<string>(incomeCategories[0].categoryId);

  const initialState: State = { message: null };
  const [state, formAction] = useActionState(createIncomeTransaction, initialState);


  return (
    <form action={ formAction } className="flex flex-col gap-4">
      { state.message &&
          <div className="flex gap-3 p-6 text-sm border-1 border-stroke-error-weak bg-fill-error-weak rounded-sm">
              <div className="flex items-center text-icon-error h-7">
                  <OctagonX />
              </div>
              <div className="flex flex-col gap-1">
                <h4 className="text-xl font-semibold text-strong">Ошибка</h4>
                <p className="text-base font-normal text-weak">{ state.message }</p>
              </div>
          </div>
      }
      <div className="flex gap-4">

        {/* Категория зачисления доходов */ }
        <div className="w-sm space-y-2">
          <Label htmlFor="category" className="flex">
            Категория
            <Asterisk size={ 12 } className="text-red-600"/>
          </Label>
          <Select name="category" required value={ currentCategory } onValueChange={ setCurrentCategory }>
            <SelectTrigger id="category" className="w-full">
              <SelectValue placeholder="Выбирите категорию"/>
            </SelectTrigger>
            <SelectContent>
              { incomeCategories.map((category) => (
                <SelectItem key={ category.categoryId } value={ category.categoryId }>
                  <div
                    className="flex w-10 h-10 bg-primary-weak [&_svg:not([class*='text-'])]:text-primary rounded-full items-center justify-center">
                    <CategoryIcon name={ category.categoryIcon } size={ 20 }/>
                  </div>
                  <div className="flex flex-col justify-between m-1">
                    <p className="flex text-xs font-medium text-weak">Доходная категория</p>
                    <div className="flex gap-0.5 items-baseline font-bold text-strong">
                      <p className="text-ms">{ category.categoryName }</p>
                    </div>
                  </div>
                </SelectItem>
              )) }
            </SelectContent>
          </Select>
        </div>
        <div className="flex items-center text-icon-success">
          <ChevronsRight size={ 36 }/>
        </div>

        {/* Кошелек для зачисления доходов */ }
        <div className="w-sm space-y-2">
          <Label htmlFor="wallet" className="flex">
            Кошелек
            <Asterisk size={ 12 } className="text-red-600"/>
          </Label>
          <Select name="wallet" required value={ currentWallet } onValueChange={ setCurrentWallet }>
            <SelectTrigger id="wallet" className="w-full">
              <SelectValue placeholder="Выбирите кошелек"/>
            </SelectTrigger>
            <SelectContent>
              { wallets.map((wallet) => (
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

        {/* Дата совершения операции */ }
        <div className="space-y-2">
          <Label htmlFor="transactionDate" className="flex">
            Дата совершения операции
            <Asterisk size={ 12 } className="text-red-600"/>
          </Label>
          <Popover>
            <PopoverTrigger asChild>
              <Button
                variant={ "outline" }
                className={ cn("w-full justify-start text-left font-normal", !transactionDate && "text-muted-foreground") }
                id="transactionDate"
                type="button"
              >
                <CalendarIcon className="mr-2 h-4 w-4"/>
                { transactionDate ? format(transactionDate, "PPP", { locale: ru }) : "Установите дату операции" }
              </Button>
            </PopoverTrigger>
            <PopoverContent className="w-auto p-0">
              <Calendar mode="single" locale={ ru } selected={ transactionDate } onSelect={ setTransactionDate }
                        required={ true }/>
            </PopoverContent>
          </Popover>
          {/* Скрытое поле ввода для хранения отформатированного значения даты */ }
          <input type="hidden" name="transactionDate" value={ transactionDate ? format(transactionDate, "yyyy-MM-dd") : "" }/>
        </div>

        <div className="space-y-2">
          {/* Сумма к зачислению */ }
          <Label htmlFor="amount" className="flex">
            Сумма к зачислению
            <Asterisk size={ 12 } className="text-red-600"/>
          </Label>
          <Input id="amount" name="amount" required type="number" min="1" step="0.01" defaultValue="100"/>
        </div>

        <div className="space-y-2">
          {/* Заметки */ }
          <Label htmlFor="notes" className="flex">
            Заметки
          </Label>
          <Input id="notes" name="notes"/>
        </div>

        { /* Экшены */ }
        <div className="space-y-2 flex justify-between">
          <Button variant="outline" type="button" asChild>
            <Link href={ "/transactions" }>Отменить</Link>
          </Button>
          <Button type="submit" className="cursor-pointer bg-primary">
            Добавить операцию
          </Button>
        </div>
      </div>
    </form>
  );
}