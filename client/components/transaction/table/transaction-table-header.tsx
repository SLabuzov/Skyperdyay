import React from 'react';

export default function TransactionTableHeader() {
  return (
    <div className="flex items-center justify-between bg-primary-weak py-4 px-5 mt-2 text-xs text-weak font-medium">
      <div className="w-[300px] max-w-[300px]">Категория</div>
      <div className="w-[100px] max-w-[100px]">Сумма</div>
      <div className="w-[180px] max-w-[180px]">Дата</div>
      <div className="w-[300px] max-w-[300px]">Примечание</div>
      <div className="w-[30px] max-w-[30px]"></div>
    </div>
  );
}
