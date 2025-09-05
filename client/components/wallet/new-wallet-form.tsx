"use client";

import React, { useActionState, useState } from "react";
import Link from "next/link";
import { Asterisk } from "lucide-react";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Button } from "@/components/ui/button";
import { Currency } from '@/lib/types';
import { createWallet, State } from "@/lib/wallet-actions";

interface FormProps {
  currencies: Currency[]
}

export default function NewWalletForm({ currencies }: FormProps) {
  const initialState: State = { message: null };
  const [state, formAction] = useActionState(createWallet, initialState);
  const [currency, setCurrency] = useState<string>(currencies[0].code);

  return (
    <form action={ formAction } className="flex flex-col">
      { state.message &&
          <p className="mt-2 text-sm text-red-500 mb-2">
            { state.message }
          </p>
      }
      <div className="flex flex-1 flex-col gap-4">
        { /* Наименование кошелька */ }
        <div className="space-y-2">
          <Label htmlFor="walletName" className="flex text-gray-600 gap-0.5">
            Название кошелька
            <Asterisk size={ 12 } className="text-red-600"/>
          </Label>
          <Input id="walletName" name="walletName" required className="bg-white"/>
        </div>

        { /* Начальный баланс кошелька */ }
        <div className="space-y-2">
          <Label htmlFor="balance" className="flex text-gray-600 gap-0.5">
            Начальный баланс кошелька
            <Asterisk size={ 12 } className="text-red-600"/>
          </Label>
          <Input
            className="bg-white border border-gray-200 focus:outline-none focus:ring-2 focus:ring-emerald-500 focus:border-transparent"
            id="balance"
            name="balance"
            type="number"
            required
            min="1"
            step="1"
          />
        </div>

        { /* Валюта кошелька */ }
        <div className="space-y-2">
          <Label htmlFor="currency" className="flex text-gray-600 gap-0.5">
            Валюта кошелька
            <Asterisk size={ 12 } className="text-red-600"/>
          </Label>
          <Select name="currency" required value={ currency } onValueChange={ setCurrency }>
            <SelectTrigger id="currency" className="w-full bg-white">
              <SelectValue/>
            </SelectTrigger>
            <SelectContent>
              { currencies.map((curr) => (
                <SelectItem key={ curr.code } value={ curr.code }>
                  { curr.symbol } - { curr.name } ({ curr.code })
                </SelectItem>
              )) }
            </SelectContent>
          </Select>
        </div>

        { /* Экшены */ }
        <div className="space-y-2 flex justify-between">
          <Button variant="outline" type="button" asChild>
            <Link href={ "/wallets" }>Отменить</Link>
          </Button>
          <Button type="submit" className="cursor-pointer bg-primary">
            Создать кошелек
          </Button>
        </div>
      </div>
    </form>
  );
}
