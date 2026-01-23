"use client";

import React from 'react';
import { useOnboardingStore } from '@/lib/stores/onboarding-store';
import { Label } from '@/components/ui/label';
import { Input } from '@/components/ui/input';
import { Asterisk } from 'lucide-react';
import StepperNavigation from '../../stepper-navigation';


export default function WalletStep() {
  const { wallet, setWallet, validateStep } = useOnboardingStore();

  const handleWalletNameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setWallet({ ...wallet, walletName: e.target.value });
  }

  const handleWalletBalanceChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const balance = parseFloat(e.target.value) || 0;
    setWallet({ ...wallet, balance: balance });
  }

  return (
    <div className="flex flex-col gap-6">
      <div>
        <h2 className="text-2xl font-bold text-strong mb-2">Создайте основной кошелек</h2>
        <p className="text-base text-weak">
          Создайте ваш первый кошелек для управления финансами
        </p>
      </div>

      <div className="space-y-4">
        <div className="space-y-2">
          <Label htmlFor="walletName" className="flex gap-0">
            Название кошелька в валюте { wallet.currencyCode }
            <Asterisk size={ 12 } className="text-red-600 ml-1"/>
          </Label>
          <Input
            id="walletName"
            value={ wallet.walletName }
            onChange={ handleWalletNameChange }
            placeholder="Например: Основной кошелек"
            className="bg-white"
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="balance" className="flex gap-0">
            Начальный баланс
            <Asterisk size={ 12 } className="text-red-600 ml-1"/>
          </Label>
          <Input
            id="balance"
            type="number"
            min="0"
            step="0.01"
            value={ wallet.balance }
            onChange={ handleWalletBalanceChange }
            placeholder="0.00"
            className="bg-white"
          />
        </div>
      </div>

      <StepperNavigation
        onNext={ () => {
        } }
        canProceed={ validateStep(6) }
        showBack={ true }
      />
    </div>
  );
}
