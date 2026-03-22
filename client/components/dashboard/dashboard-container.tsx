"use client";


import { AnalyticsSummary } from "@/lib/types";
import NetIncomeStatisticCard from "@/components/dashboard/net-income-statistic-card";
import IncomeStatisticCard from "@/components/dashboard/income-statistic-card";
import ExpenseStatisticCard from "@/components/dashboard/expense-statistic-card";
import BalanceCard from "@/components/dashboard/balance-card";
import { CategoriesBreakdownChart } from "@/components/dashboard/categories-breakdown-chart";

interface Props {
  allData: AnalyticsSummary
}

export default function DashboardContainer({ allData }: Props) {
  const {
    currencyCode,
    availableBalance,
    balanceMetrics
  } = allData.financialReports.find(it => it.isMainCurrency)!;

  const {
    periodInterval,
    incomeTotal,
    expenseTotal,
    netIncomeTotal,
    incomeDiff,
    expenseDiff,
    netIncomeDiff,
    incomePercentage,
    expensePercentage,
    netIncomePercentage
  } = balanceMetrics;

  const {
    categoryBreakdownSummary
  } = allData;

  return (
    <div className="space-y-6">
      {/* Строка 1: Основные метрики */ }
      <div className="grid grid-cols-12 gap-4">
        <div className="col-span-3 flex">
          <NetIncomeStatisticCard currencyCode={ currencyCode }
                                  interval={ periodInterval }
                                  netIncomeTotal={ netIncomeTotal }
                                  netIncomeDiff={ netIncomeDiff }
                                  netIncomePercentage={ netIncomePercentage }/>
        </div>
        <div className="col-span-3 flex">
          <IncomeStatisticCard currencyCode={ currencyCode }
                               interval={ periodInterval }
                               incomeTotal={ incomeTotal }
                               incomeDiff={ incomeDiff }
                               incomePercentage={ incomePercentage }/>
        </div>
        <div className="col-span-3 flex">
          <ExpenseStatisticCard currencyCode={ currencyCode }
                                interval={ periodInterval }
                                expenseTotal={ expenseTotal }
                                expenseDiff={ expenseDiff }
                                expensePercentage={ expensePercentage }/>
        </div>
        <div className="col-span-3 flex">
          <BalanceCard currencyCode={ currencyCode } availableBalance={ availableBalance }/>
        </div>
      </div>

      {/* Строка 2: Распределение доходов/расходов по категориям */ }
      <div className="grid grid-cols-12 gap-4">
        <div className="col-span-6">
          <CategoriesBreakdownChart
            categoryType={ 'EXPENSE' }
            categories={ categoryBreakdownSummary.expenseBreakdownSummary }
            totalAmount={ categoryBreakdownSummary.totalExpenseAmount }
            currencyCode={ categoryBreakdownSummary.mainCurrencyCode }
          />
        </div>
        <div className="col-span-6">
          <CategoriesBreakdownChart
            categoryType={ 'INCOME' }
            categories={ categoryBreakdownSummary.incomeBreakdownSummary }
            totalAmount={ categoryBreakdownSummary.totalIncomeAmount }
            currencyCode={ categoryBreakdownSummary.mainCurrencyCode }
          />
        </div>
      </div>
    </div>
  );
}
