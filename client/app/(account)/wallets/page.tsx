import { Wallet } from "lucide-react";

export default function WalletsPage() {
  return (
    <div className="flex flex-col flex-1 gap-4">
      <div>
        <h1 className="text-base font-bold text-[#1A1615]">Информация по кошельку</h1>
      </div>
      <div className="flex flex-col flex-1 rounded-xl items-center justify-center bg-[#ecf4e9] text-[#6B7271]">
        <Wallet className="w-36 h-36"/>
        <h1 className="text-3xl font-semibold">Выберите кошелек из списка или создайте новый</h1>
      </div>

    </div>
  );
}