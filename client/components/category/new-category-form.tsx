"use client";

import React, { useActionState, useState } from "react";
import Link from "next/link";
import { Asterisk } from "lucide-react";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { createCategory, State } from "@/lib/category-actions";
import { RadioGroup, RadioGroupItem } from '@/components/ui/radio-group';
import { IconPicker } from '@/components/icon-picker';
import { IconName } from '@/lib/icon-map';

const types = [
  {
    type: "INCOME",
    title: "Доходная",
    description: "Для пополнения кошелька"
  },
  {
    type: "EXPENSE",
    title: "Расходная",
    description: "Для списания с кошелька"
  }
]


export default function NewCategoryForm() {
  const initialState: State = { message: null };
  const [state, formAction] = useActionState(createCategory, initialState);
  const [iconName, setIconName] = useState<IconName | null>(null);


  return (
    <form action={ formAction } className="flex flex-col">
      { state.message &&
          <p className="mt-2 text-sm text-red-500 mb-2">
            { state.message }
          </p>
      }
      <div className="flex flex-1 flex-col gap-4">
        { /* Наименование категории */ }
        <div className="space-y-2">
          <Label htmlFor="name" className="flex text-gray-600 gap-0.5">
            Наименование категории
            <Asterisk size={ 12 } className="text-red-600"/>
          </Label>
          <Input id="name" name="name" required className="bg-white"/>
        </div>

        { /* Тип категории */ }
        <fieldset className="space-y-2">
          <legend className="flex text-sm font-medium">
            Тип категории
            <Asterisk size={ 12 } className="text-red-600"/>
          </legend>
          <RadioGroup id="type" name="type" defaultValue="EXPENSE" className="grid grid-cols-2 gap-3">
            { types.map((type) => (
              <Label
                className="has-[[data-state=checked]]:border-ring has-[[data-state=checked]]:bg-primary/5 flex items-start gap-3 rounded-lg border p-3"
                key={ type.type }
              >
                <RadioGroupItem
                  value={ type.type }
                  id={ type.type }
                  className="data-[state=checked]:border-primary"
                />
                <div className="grid gap-1 font-normal">
                  <div className="font-medium">{ type.title }</div>
                  <div className="text-muted-foreground pr-2 text-xs leading-snug text-balance">
                    { type.description }
                  </div>
                </div>
              </Label>
            )) }
          </RadioGroup>
        </fieldset>

        { /* Иконка категории */ }
        <div className="space-y-2">
          <Label htmlFor="icon" className="flex text-gray-600 gap-0.5">
            Иконка категории
            <Asterisk size={ 12 } className="text-red-600"/>
          </Label>
          <IconPicker
            selectedIcon={ iconName }
            onSelect={ setIconName }
            size={ 28 }
          />
          {/* Hidden input to store the formatted date value */ }
          <input type="hidden" name="icon" value={ iconName || '' }/>
        </div>

        { /* Экшены */ }
        <div className="space-y-2 flex justify-between">
          <Button variant="outline" type="button" asChild>
            <Link href={ "/categories" }>Отменить</Link>
          </Button>
          <Button type="submit" className="cursor-pointer bg-primary">
            Создать категорию
          </Button>
        </div>
      </div>
    </form>
  );
}