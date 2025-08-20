"use client";

import React from "react";
import { usePathname } from "next/navigation";
import Link from "next/link";
import { ArrowLeftRight, WalletCards, LayoutDashboard, Wallet } from "lucide-react";
import { cn } from "@/lib/utils";

const navigation = [
  { name: "Общая аналитика", href: "/dashboard", icon: LayoutDashboard },
  { name: "Кошельки", href: "/wallets", icon: WalletCards },
  { name: "Переводы между кошельками", href: "/payments", icon: ArrowLeftRight },
  { name: "Операции", href: "/transactions", icon: ArrowLeftRight },
];

export default function Sidebar() {

  const pathname = usePathname();

  return (
    <div className="w-xs bg-header-background border-r h-screen flex flex-col bg-[#ecf4e9]">
      {/* Логотип */ }
      <div className="p-4">
        <div className="flex items-center">
          <Link href="/">
            <Wallet className="h-9 w-9" color="#1E4841"/>
          </Link>
        </div>
      </div>

      {/* Пункты меню */ }
      <nav className="flex-1 px-4 pt-6">
        <ul className="space-y-1">
          { navigation.map((item) => {
            const isActive = pathname === item.href;
            return (
              <li key={ item.name }>
                <Link
                  href={ item.href }
                  className={ cn(
                    "flex items-center gap-3 px-3 py-2.5 rounded-lg text-sm font-medium transition-colors relative",
                    isActive ? "bg-[#BBF49C] text-[#1E4841] border-1 border-emerald-100" : "text-gray-600 hover:bg-[#E0F2E0] hover:text-gray-900 border-1 border-transparent"
                  ) }
                >
                  <item.icon className="w-5 h-5"/>
                  {item.name}
                </Link>
              </li>
            );
          }) }
        </ul>
      </nav>
    </div>
  );
}
