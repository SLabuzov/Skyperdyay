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
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import { TransactionInfo } from '@/lib/types';
import React, { JSX, useState } from 'react';
import { format } from 'date-fns';
import { ru } from 'date-fns/locale';
import { CategoryIcon } from '@/components/category/category-icon';
import { cn } from "@/lib/utils";
import { Button } from "@/components/ui/button";

interface Props {
  transactions: TransactionInfo[]
}

export default function TransactionTable({ transactions }: Props): JSX.Element {

  const itemsPerPage = 10;
  const [currentPage, setCurrentPage] = useState<number>(1);

  const totalPages = Math.ceil(transactions.length / itemsPerPage);
  const startIndex = (currentPage - 1) * itemsPerPage;
  const paginatedTransactions = React.useMemo(() => {
    return transactions.slice(startIndex, startIndex + itemsPerPage);
  }, [startIndex]);

  const handlePageChange = (page: number) => {
    if (page >= 1 && page <= totalPages) {
      setCurrentPage(page);
    }
  };

  const getPageNumbers = () => {
    const delta = 2;
    const range = [];
    const rangeWithDots = [];
    let l;

    range.push(1);
    for (let i = currentPage - delta; i <= currentPage + delta; i++) {
      if (i < totalPages && i > 1) {
        range.push(i);
      }
    }
    range.push(totalPages);

    for (let i of range) {
      if (l) {
        if (i - l === 2) {
          rangeWithDots.push(l + 1);
        } else if (i - l !== 1) {
          rangeWithDots.push('ellipsis');
        }
      }
      rangeWithDots.push(i);
      l = i;
    }

    return rangeWithDots;
  };

  return (
    <div className="flex flex-col w-full my-4">
      <div className="flex-grow max-h-[calc(100vh-220px)] overflow-y-auto">
        <Table className="min-w-full">
          <TableHeader>
            <TableRow>
              <TableHead className="w-sm text-weak">Категория</TableHead>
              <TableHead className="text-weak text-right">Сумма</TableHead>
              <TableHead className="text-weak">Дата</TableHead>
              <TableHead className="text-weak w-sm">Примечание</TableHead>
            </TableRow>
          </TableHeader>

          <TableBody>
            { paginatedTransactions.map(transaction => {
                const amountColorStyle = transaction.category.categoryType === 'INCOME' ? 'text-success' : 'text-error';

                return (
                  <TableRow key={ transaction.transactionId }>
                    <TableCell className="flex items-center gap-3 w-sm font-medium py-3">
                      <div
                        className="flex w-10 h-10 bg-primary-weak rounded-full items-center justify-center text-primary">
                        <CategoryIcon name={ transaction.category.categoryIcon } size={ 20 }/>
                      </div>
                      <div>
                        <p className="text-strong text-sm">{ transaction.category.categoryName }</p>
                        <p className="text-weak text-xs">{ transaction.wallet.walletName }</p>
                      </div>
                    </TableCell>
                    <TableCell className="text-right">
                      <div className="flex gap-0.5 items-baseline justify-end font-medium py-3">
                        <p className={ cn("text-base", amountColorStyle) }>
                          { new Intl.NumberFormat("ru-RU").format(transaction.amount) }
                        </p>
                        <p className={ cn("text-sm", amountColorStyle) }>
                          { transaction.wallet.walletCurrency.code }
                        </p>
                      </div>
                    </TableCell>
                    <TableCell>{ format(transaction.transactionDate, "PPP", { locale: ru }) }</TableCell>
                    <TableCell className="w-sm text-strong font-medium text-xs">
                      { transaction.notes }
                    </TableCell>
                  </TableRow>
                )
              }
            ) }
          </TableBody>
        </Table>
      </div>

      {/* Пагинация — всегда внизу */ }
      <Pagination className="justify-end mt-4">
        <PaginationContent>
          <PaginationItem>
            <Button
              variant="outline"
              size="sm"
              onClick={ () => handlePageChange(1) }
              disabled={ currentPage === 1 }
            >
              Первая
            </Button>
          </PaginationItem>

          <PaginationItem>
            <PaginationPrevious
              onClick={ () => handlePageChange(currentPage - 1) }
              className={ currentPage === 1 ? "pointer-events-none opacity-50" : "cursor-pointer" }
            />
          </PaginationItem>

          { getPageNumbers().map((page, index) => (
            <PaginationItem key={ index }>
              { page === 'ellipsis' ? (
                <PaginationEllipsis/>
              ) : (
                <PaginationLink
                  onClick={ () => handlePageChange(page as number) }
                  isActive={ currentPage === page }
                  className="cursor-pointer"
                >
                  { page }
                </PaginationLink>
              ) }
            </PaginationItem>
          )) }

          <PaginationItem>
            <PaginationNext
              onClick={ () => handlePageChange(currentPage + 1) }
              className={ currentPage === totalPages ? "pointer-events-none opacity-50" : "cursor-pointer" }
            />
          </PaginationItem>

          <PaginationItem>
            <Button
              variant="outline"
              size="sm"
              onClick={ () => handlePageChange(totalPages) }
              disabled={ currentPage === totalPages }
            >
              Последняя
            </Button>
          </PaginationItem>
        </PaginationContent>
      </Pagination>
    </div>
  );
}
