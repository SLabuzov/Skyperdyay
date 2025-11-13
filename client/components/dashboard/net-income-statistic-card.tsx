import { Banknote } from "lucide-react";
import PercentageBadge from "@/components/dashboard/percentage-badge";

interface Props {
  currencyCode: string
  interval: number
  netIncomeTotal: number
  netIncomeDiff: number
  netIncomePercentage: number
}

export default function NetIncomeStatisticCard({
                                                 currencyCode,
                                                 interval,
                                                 netIncomeTotal,
                                                 netIncomeDiff,
                                                 netIncomePercentage
                                               }: Props) {


  return (
    <div className="flex flex-1 items-center justify-between gap-2 border border-stoke-weak rounded-md p-4">
      <div className="flex flex-col gap-2">
        <div className="flex gap-2 items-center">
          <div className="flex bg-primary-weak text-primary rounded-full p-2">
            <Banknote size={ 16 }/>
          </div>
          <p className="text-strong text-sm font-medium">Чистый доход</p>
        </div>
        <div className="flex gap-0.5 items-baseline">
          <p className="text-primary text-2xl font-bold">{ new Intl.NumberFormat("ru-RU").format(netIncomeTotal) }</p>
          <p className="text-primary text-lg font-bold">{ currencyCode }</p>
        </div>
      </div>
      <div className="flex flex-col gap-2">
        <div className="flex items-center justify-end">
          <PercentageBadge percentageChange={ netIncomePercentage }/>
        </div>
        <div className="flex gap-0.5 items-baseline">
          <p className="text-primary text-xs font-bold">{ new Intl.NumberFormat("ru-RU").format(netIncomeDiff) }</p>
          <p className="text-strong text-xs font-normal">за { interval } дней</p>
        </div>
      </div>
    </div>
  );
}
