import React from "react";
import { analyticsApiService } from "@/services/analytics-api-service";
import DashboardContainer from "@/components/dashboard/dashboard-container";

export default async function DashboardPage() {

  const analyticsSummary = await analyticsApiService.dashboard();

  return (
    <div className="flex flex-col flex-1 gap-4 px-8">
      <div className="flex items-center justify-between h-10">
        <h1 className="text-base font-bold text-gray-600">Аналитика</h1>
      </div>
      <div className="flex flex-col flex-1">
        <DashboardContainer allData={ analyticsSummary }/>
      </div>
    </div>
  );
}