"use client";

import { CategoryInfo, WalletInfo } from '@/lib/types';
import { Label } from '@/components/ui/label';
import { Calendar } from '@/components/ui/calendar';
import { Button } from '@/components/ui/button';
import React, { useActionState, useState } from 'react';
import { cn } from '@/lib/utils';
import { Asterisk, CalendarIcon, ChevronsRight } from 'lucide-react';
import { Popover, PopoverContent, PopoverTrigger } from '@/components/ui/popover';
import { format } from 'date-fns';
import { ru } from 'date-fns/locale';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { CategoryIcon } from '@/components/category/category-icon';
import { Separator } from '@/components/ui/separator';
import { Input } from '@/components/ui/input';
import Link from 'next/link';
import { createExpenseTransaction, State } from '@/lib/transaction-actions';

interface FormProps {
  wallets: WalletInfo[]
  expenseCategories: CategoryInfo[]
}

export default function NewExpenseTransactionForm({ wallets, expenseCategories }: FormProps) {

  const [transactionDate, setTransactionDate] = useState<Date>(new Date());
  const [currentWallet, setCurrentWallet] = useState<string>(wallets[0].walletId);
  const [currentCategory, setCurrentCategory] = useState<string>(expenseCategories[0].categoryId);

  const initialState: State = { message: null };
  const [state, formAction] = useActionState(createExpenseTransaction, initialState);


  return (
    <form action={ formAction } className="flex flex-col gap-4">
      { state.message &&
          <p className="mt-2 text-sm text-red-500 mb-2">
            { state.message }
          </p>
      }
      <div className="flex gap-4">

        {/* Кошелек для списания */ }
        <div className="flex-1 space-y-2">
          <Label htmlFor="wallet" className="flex">
            Кошелек
            <Asterisk size={ 12 } className="text-red-600"/>
          </Label>
          <Select name="wallet" required value={ currentWallet } onValueChange={ setCurrentWallet }>
            <SelectTrigger id="wallet" className="w-full h-[80px]">
              <SelectValue placeholder="Выбирите кошелек"/>
            </SelectTrigger>
            <SelectContent>
              { wallets.map((wallet) => (
                <SelectItem key={ wallet.walletId } value={ wallet.walletId }>
                  <div className="flex w-14 h-14 bg-primary-weak rounded-full items-center justify-center">
                    <p className="text-2xl leading-none font-bold text-primary">{ wallet.walletCurrency.symbol }</p>
                  </div>
                  <div className="flex flex-col justify-between m-1">
                    <p className="text-sm font-medium text-weak">{ wallet.walletName }</p>
                    <div className="flex gap-0.5 items-baseline font-bold text-strong">
                      <p className="text-2xl">{ new Intl.NumberFormat("ru-RU").format(wallet.walletBalance) }</p>
                      <p className="text-sm">{ wallet.walletCurrency.code }</p>
                    </div>
                  </div>
                </SelectItem>
              )) }
            </SelectContent>
          </Select>
        </div>
        <div className="flex items-center text-icon-error">
          <ChevronsRight size={ 36 }/>
        </div>

        {/* Категория списания */ }
        <div className="flex-1 space-y-2">
          <Label htmlFor="category" className="flex">
            Категория
            <Asterisk size={ 12 } className="text-red-600"/>
          </Label>
          <Select name="category" required value={ currentCategory } onValueChange={ setCurrentCategory }>
            <SelectTrigger id="category" className="w-full h-[80px]">
              <SelectValue placeholder="Выбирите категорию"/>
            </SelectTrigger>
            <SelectContent>
              { expenseCategories.map((category) => (
                <SelectItem key={ category.categoryId } value={ category.categoryId }>
                  <div
                    className="flex w-14 h-14 bg-primary-weak [&_svg:not([class*='text-'])]:text-primary rounded-full items-center justify-center">
                    <CategoryIcon name={ category.categoryIcon } size={ 26 }/>
                  </div>
                  <div className="flex flex-col justify-between m-1">
                    <p className="flex text-sm font-medium text-weak">Доходная категория</p>
                    <div className="flex gap-0.5 items-baseline font-bold text-strong">
                      <p className="text-2xl">{ category.categoryName }</p>
                    </div>
                  </div>
                </SelectItem>
              )) }
            </SelectContent>
          </Select>
        </div>
      </div>

      <Separator/>

      <div className="flex flex-col w-sm gap-4">
        {/* Дата совершения операции */ }
        <div className="space-y-2">
          <Label htmlFor="startDate" className="flex">
            Дата совершения операции
            <Asterisk size={ 12 } className="text-red-600"/>
          </Label>
          <Popover>
            <PopoverTrigger asChild>
              <Button
                variant={ "outline" }
                className={ cn("w-full justify-start text-left font-normal", !transactionDate && "text-muted-foreground") }
                id="startDate"
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
          {/* Hidden input to store the formatted date value */ }
          <input type="hidden" name="startDate" value={ transactionDate ? format(transactionDate, "yyyy-MM-dd") : "" }/>
        </div>

        <div className="space-y-2">
          <Label htmlFor="amount" className="flex">
            Сумма к списанию
            <Asterisk size={ 12 } className="text-red-600"/>
          </Label>
          <Input id="amount" name="amount" required type="number" min="1" step="1" defaultValue="100"/>
        </div>

        <div className="space-y-2">
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