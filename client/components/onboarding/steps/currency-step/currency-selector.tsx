"use client";

import React, { useMemo, useState } from 'react';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Asterisk, Search } from 'lucide-react';
import { Currency } from '@/lib/types';

interface Props {
  currencies: Currency[];
  value: Currency | null;
  onChange: (currency: Currency) => void;
}

export default function CurrencySelector({ currencies, value, onChange }: Props) {
  const [searchQuery, setSearchQuery] = useState('');

  const filteredCurrencies = useMemo(() => {
    if (!searchQuery) {
      return currencies;
    }
    const query = searchQuery.toLowerCase();
    return currencies.filter(
      curr =>
        curr.code.toLowerCase().includes(query) ||
        curr.name.toLowerCase().includes(query) ||
        curr.symbol.toLowerCase().includes(query)
    );
  }, [currencies, searchQuery]);

  const displayValue = value?.code || '';

  return (
    <div className="space-y-2">
      <Label htmlFor="currency" className="flex gap-0">
        Валюта
        <Asterisk size={ 12 } className="text-red-600 ml-1"/>
      </Label>

      <div className="relative">
        <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-weak" size={ 16 }/>
        <Input
          type="text"
          placeholder="Поиск валюты..."
          value={ searchQuery }
          onChange={ (e) => setSearchQuery(e.target.value) }
          className="pl-10 mb-2"
        />
      </div>

      <Select
        value={ displayValue }
        onValueChange={ (code) => {
          const curr = currencies.find(c => c.code === code);
          if (curr) {
            onChange(curr);
          }
        } }
      >
        <SelectTrigger id="currency" className="w-full">
          <SelectValue placeholder="Выберите валюту"/>
        </SelectTrigger>
        <SelectContent className="max-h-[300px]">
          { filteredCurrencies.map((curr) => (
            <SelectItem key={ curr.code } value={ curr.code }>
              <div className="flex items-center gap-2">
                <span className="font-semibold">{ curr.symbol }</span>
                <span className="text-weak">({ curr.code })</span>
                <span>{ curr.name }</span>
              </div>
            </SelectItem>
          )) }
        </SelectContent>
      </Select>
    </div>
  );
}
