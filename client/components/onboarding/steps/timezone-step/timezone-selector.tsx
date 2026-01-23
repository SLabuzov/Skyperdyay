"use client";

import React, { useMemo, useState } from 'react';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { Label } from '@/components/ui/label';
import { Asterisk, Search } from 'lucide-react';
import { Input } from '@/components/ui/input';
import { Timezone } from '@/lib/types';

interface Props {
  timezones: Timezone[];
  value: Timezone | null;
  onChange: (timezone: Timezone) => void;
}

export default function TimezoneSelector({ timezones, value, onChange }: Props) {
  const [searchQuery, setSearchQuery] = useState<string>('');

  const filteredTimezones = useMemo(() => {
    if (!searchQuery) {
      return timezones;
    }
    const query = searchQuery.toLowerCase();
    return timezones.filter(tz =>
      tz.code.toLowerCase().includes(query) ||
      tz.name.toLowerCase().includes(query)
    );
  }, [timezones, searchQuery]);

  const displayValue = value?.code || '';

  return (
    <div className="space-y-2">
      <Label htmlFor="timezone" className="flex gap-0">
        Часовой пояс
        <Asterisk size={ 12 } className="text-red-600 ml-1"/>
      </Label>

      <div className="relative">
        <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-weak" size={ 16 }/>
        <Input
          type="text"
          placeholder="Поиск часового пояса..."
          value={ searchQuery }
          onChange={ (e) => setSearchQuery(e.target.value) }
          className="pl-10 mb-2"
        />
      </div>

      <Select
        value={ displayValue }
        onValueChange={ (code) => {
          const tz = timezones.find(t => t.code === code);
          if (tz) {
            onChange(tz);
          }
        } }
      >
        <SelectTrigger id="timezone" className="w-full">
          <SelectValue placeholder="Выберите часовой пояс"/>
        </SelectTrigger>
        <SelectContent className="max-h-[300px]">
          { filteredTimezones.map((tz) => (
            <SelectItem key={ tz.code } value={ tz.code }>
              { tz.name } ({ tz.code })
            </SelectItem>
          )) }
        </SelectContent>
      </Select>
    </div>
  );
}
