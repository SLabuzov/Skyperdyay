import React, { Suspense } from "react";
import { analyticsApiService } from "@/services/analytics-api-service";
import DashboardContainer from "@/components/dashboard/dashboard-container";
import DashboardSkeleton from "@/components/dashboard/dashboard-skeleton";

async function DashboardDataFetcher() {
  const analyticsSummary = await analyticsApiService.dashboard();

  return (
    <DashboardContainer allData={ analyticsSummary }/>
  );
}

export default async function DashboardPage() {

  return (
    <div className="flex flex-col flex-1 gap-4 px-8">
      <div className="flex items-center justify-between h-10">
        <h1 className="text-base font-bold text-gray-600">Аналитика</h1>
      </div>
      <div className="flex flex-col flex-1">
        <Suspense fallback={ <DashboardSkeleton/> }>
          <DashboardDataFetcher/>
        </Suspense>
      </div>
    </div>
  );
}
