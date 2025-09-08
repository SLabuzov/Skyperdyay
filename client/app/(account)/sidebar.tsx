"use client";

import React from "react";
import { usePathname } from "next/navigation";
import Link from "next/link";
import {Banknote, HandCoins, LayoutDashboard, WalletCards} from "lucide-react";
import { cn } from "@/lib/utils";
import Logo from "@/components/logo";

const navigation = [
  { name: "Общая аналитика", href: "/dashboard", icon: LayoutDashboard },
  { name: "Платежи", href: "/transactions", icon: HandCoins },
  { name: "Кошельки", href: "/wallets", icon: WalletCards },
  { name: "Финансовые категории", href: "/categories", icon: Banknote },
];

export default function Sidebar() {
  const pathname = usePathname();

  return (
    <div className="h-screen flex flex-col w-xs">
      <Logo/>

      {/* Пункты меню */ }
      <nav className="flex-1 px-8 pt-6">
        <ul className="space-y-1">
          { navigation.map((item) => {
            const isActive = pathname.startsWith(item.href);
            return (
              <li key={ item.name }>
                <Link
                  href={ item.href }
                  className={ cn(
                    "flex items-center gap-3 px-3 py-2.5 rounded-lg text-sm font-medium transition-colors",
                    isActive ? "bg-[#BBF49C] text-primary" : "text-gray-600 hover:bg-[#E0F2E0] hover:text-gray-900"
                  ) }
                >
                  <item.icon className="w-5 h-5"/>
                  { item.name }
                </Link>
              </li>
            );
          }) }
        </ul>
      </nav>
    </div>
  );
}