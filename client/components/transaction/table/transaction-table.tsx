"use client";

import {
  Pagination,
  PaginationContent,
  PaginationEllipsis,
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious
} from '@/components/ui/pagination';
import {Table, TableBody, TableCell, TableHead, TableHeader, TableRow} from '@/components/ui/table';
import {TransactionInfo} from '@/lib/types';
import React, {JSX} from 'react';
import {format} from 'date-fns';
import {ru} from 'date-fns/locale';
import {CategoryIcon} from '@/components/category/category-icon';

interface Props {
  transactions: TransactionInfo[]
}

export default function TransactionTable({transactions}: Props): JSX.Element {
  return (
    <div className="flex flex-col w-full my-4">
      {/* Фильтры или другие элементы сверху — если есть */}

      {/* ✅ КЛЮЧЕВОЙ ЭЛЕМЕНТ: Обёртка с flex-grow и max-height */}
      <div className="flex-grow max-h-[calc(100vh-220px)] overflow-y-auto">
        {/* ✅ Теперь sticky работает, потому что родитель — блочный контейнер (div) */}
        <Table className="min-w-full">
          <TableHeader>
            <TableRow className="bg-background sticky top-0 border-stroke-weak z-10">
              <TableHead className="w-sm text-weak">Категория</TableHead>
              <TableHead className="text-weak text-right">Сумма</TableHead>
              <TableHead className="text-weak">Дата</TableHead>
              <TableHead className="text-weak w-sm">Примечание</TableHead>
            </TableRow>
          </TableHeader>

          <TableBody>
            {transactions.map(transaction => (
              <TableRow key={transaction.transactionId}>
                <TableCell className="flex items-center gap-3 w-sm font-medium py-3">
                  <div className="flex w-10 h-10 bg-primary-weak rounded-full items-center justify-center">
                    <CategoryIcon name={transaction.category.categoryIcon} size={20}/>
                  </div>
                  <div>
                    <p className="text-strong text-sm">{transaction.category.categoryName}</p>
                    <p className="text-weak text-xs">{transaction.wallet.walletName}</p>
                  </div>
                </TableCell>
                <TableCell className="text-right">
                  <div className="flex gap-0.5 items-baseline font-medium py-3">
                    <p className="text-strong text-sm">{new Intl.NumberFormat("ru-RU").format(transaction.amount)}</p>
                    <p className="text-weak text-[8px]">{transaction.wallet.walletCurrency.code}</p>
                  </div>
                </TableCell>
                <TableCell>{format(transaction.transactionDate, "PPP", {locale: ru})}</TableCell>
                <TableCell className="w-sm text-strong font-medium text-xs">
                  {transaction.notes}
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </div>

      {/* Пагинация — всегда внизу */}
      <Pagination className="mt-4">
        <PaginationContent>
          <PaginationItem>
            <PaginationPrevious href="#"/>
          </PaginationItem>
          <PaginationItem>
            <PaginationLink href="#">1</PaginationLink>
          </PaginationItem>
          <PaginationItem>
            <PaginationLink href="#" isActive>
              2
            </PaginationLink>
          </PaginationItem>
          <PaginationItem>
            <PaginationLink href="#">3</PaginationLink>
          </PaginationItem>
          <PaginationItem>
            <PaginationEllipsis/>
          </PaginationItem>
          <PaginationItem>
            <PaginationNext href="#"/>
          </PaginationItem>
        </PaginationContent>
      </Pagination>
    </div>
  );
}
