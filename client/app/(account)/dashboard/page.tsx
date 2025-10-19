import React from "react";
import { analyticsApiService } from '@/services/analytics-api-service';
import CashFlowSection from '@/components/dashboard/cash-flow/cash-flow-section';
import { walletApiService } from '@/services/wallet-api-service';
import WalletsSection from '@/components/dashboard/wallets/wallets-section';
import FinanceScoreSection from '@/components/dashboard/wallets/finance-score-section';
import { FinanceScoreResult } from '@/lib/types';

export default async function DashboardPage() {

  const cashFlowData = await analyticsApiService.cashFlow('2025-01-01', '2025-12-31');
  const wallets = await walletApiService.getWallets();
  const financeScoreResult: FinanceScoreResult = {
    financeQuality: "Отличный",
    savingPercent: 90
  };

  return (
    <div className="flex flex-col flex-1 gap-4 px-8">
      <div className="flex items-center justify-between h-10">
        <h1 className="text-base font-bold text-gray-600">DashboardPage</h1>
      </div>
      <div className="flex flex-col flex-1">
        <div className="flex flex-row gap-4">
          <div className="flex-2">
            <CashFlowSection data={ cashFlowData }/>
          </div>
          <div className="flex-1">

          </div>
          <div className="flex flex-col flex-1 gap-5">
            <FinanceScoreSection data={ financeScoreResult }/>
            <WalletsSection data={ wallets }/>
          </div>
        </div>


      </div>
    </div>
  );
}
