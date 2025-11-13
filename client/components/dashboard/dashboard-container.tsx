"use client";


import { AnalyticsSummary } from "@/lib/types";
import NetIncomeStatisticCard from "@/components/dashboard/net-income-statistic-card";
import IncomeStatisticCard from "@/components/dashboard/income-statistic-card";
import ExpenseStatisticCard from "@/components/dashboard/expense-statistic-card";
import BalanceCard from "@/components/dashboard/balance-card";

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

  return (
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
  );
}
