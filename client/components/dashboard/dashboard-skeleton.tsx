import { Skeleton } from "@/components/ui/skeleton";
import React from "react";

export default function DashboardSkeleton() {
  return (
    <div className="grid grid-cols-12 gap-4">
      { [...Array(4)].map((_, i) => (
        <div key={ i } className="col-span-3">
          <Skeleton className="h-32 w-full"/>
        </div>
      )) }
    </div>
  );
}
