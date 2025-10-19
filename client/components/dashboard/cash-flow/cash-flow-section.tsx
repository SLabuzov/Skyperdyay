"use client";

import { CashFlowInfo } from '@/lib/types';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import React, { useState } from 'react';
import { Bar, BarChart, CartesianGrid, ReferenceLine, ResponsiveContainer, Tooltip, XAxis, YAxis } from 'recharts';
import { format } from 'date-fns';
import { ru } from 'date-fns/locale';

interface Props {
  data: CashFlowInfo[]
}

export default function CashFlowSection({ data }: Props) {

  return (
    <div className="flex flex-col border-1 border-stroke-weak rounded-md p-4 gap-4">
      <CashFlowSectionHeader data={ data }/>
      <CashFlowSectionInfo data={ data }/>
      <CashFlowSectionChart data={ data }/>
    </div>
  );
}

function CashFlowSectionHeader({ data }: Props) {
  const distinctCurrencies: string[] = [...new Set(data.map(item => item.currencyCode))];
  const [currentCurrency, setCurrentCurrency] = useState<string>(distinctCurrencies[0]);

  return (
    <div className="flex flex-row items-center justify-between">
      <p className="text-base font-bold text-strong">Денежный поток</p>
      <Select name="currency" required value={ currentCurrency } onValueChange={ setCurrentCurrency }>
        <SelectTrigger id="category">
          <SelectValue placeholder="Выбирите категорию"/>
        </SelectTrigger>
        <SelectContent>
          { distinctCurrencies.map((currency) => (
            <SelectItem key={ currency } value={ currency }>
              { currency }
            </SelectItem>
          )) }
        </SelectContent>
      </Select>
    </div>
  );
}

function CashFlowSectionInfo({ data }: Props) {

  const totalBalance = data.reduce((acc, cur) => acc + cur.incomeAmount + cur.expenseAmount, 0);

  return (
    <div className="flex flex-row items-center justify-between">
      <div>
        <p className="text-xs text-strong">Итоговый баланс</p>
        <p className="text-2xl font-bold text-primary">{ new Intl.NumberFormat("ru-RU").format(totalBalance) }</p>
      </div>
      <div className="flex items-center gap-6 text-xs font-semibold text-strong">
        <div className="flex items-center gap-2">
          <div className="w-2 h-2 bg-primary"/>
          <p>Доходы</p>
        </div>
        <div className="flex items-center gap-2">
          <div className="w-2 h-2 bg-secondary"/>
          <p>Расходы</p>
        </div>
      </div>
    </div>
  );
}

function CashFlowSectionChart({ data }: Props) {
  return (
    <ResponsiveContainer className="h-[182px]!">
      <BarChart
        className="text-weak [&_.recharts-text]:text-xs"
        data={ data }
        stackOffset="sign"
        margin={ {
          left: 4,
          right: 0,
          top: 12,
          bottom: 18,
        } }
      >
        <CartesianGrid vertical={ false } stroke="currentColor" className="text-gray-100"/>
        <XAxis
          dataKey="month"
          fill="currentColor"
          axisLine={ false }
          tickLine={ false }
          tickMargin={ 11 }
          interval="preserveStartEnd"
          tickFormatter={ (value) => format(new Date(value), "LLL", { locale: ru }) }
        />
        <YAxis
          fill="currentColor"
          axisLine={ false }
          tickLine={ false }
          interval="preserveStartEnd"
          tickFormatter={ (value) => Number(value).toLocaleString() }
        />
        <Tooltip/>
        <ReferenceLine y={ 0 } stroke="currentColor" className="text-gray-300"/>
        <Bar dataKey="incomeAmount" fill="#1e4841" type="monotone" stackId="stack" maxBarSize={ 25 }
             radius={ [6, 6, 0, 0] }/>
        <Bar dataKey="expenseAmount" fill="#BBF49C" type="monotone" stackId="stack" maxBarSize={ 25 }
             radius={ [6, 6, 0, 0] }/>
      </BarChart>
    </ResponsiveContainer>
  );
}