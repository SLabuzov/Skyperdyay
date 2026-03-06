"use client";

import React from "react";
import Link from "next/link";
import { usePathname } from "next/navigation";
import { Home } from "lucide-react";
import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbPage,
  BreadcrumbSeparator,
} from "@/components/ui/breadcrumb";

const routeLabels: Record<string, string> = {
  dashboard: "Главная",
  wallets: "Кошельки",
  new: "Создание",
  transactions: "Транзакции",
  "top-up": "Пополнение",
  withdraw: "Списание",
  transfer: "Перевод",
  import: "Импорт",
  categories: "Категории",
  analytics: "Аналитика",
  cashflow: "Денежный поток",
};

interface BreadcrumbItemData {
  label: string;
  href: string;
  isLast: boolean;
  isHome: boolean;
}

function generateBreadcrumbs(pathname: string): BreadcrumbItemData[] {
  const segments = pathname.split("/").filter(Boolean);
  const breadcrumbs: BreadcrumbItemData[] = [];

  breadcrumbs.push({
    label: "Главная",
    href: "/dashboard",
    isLast: segments.length === 0 || segments[0] === "dashboard",
    isHome: true,
  });

  if (segments.length === 0 || segments[0] === "dashboard") {
    return breadcrumbs;
  }

  let currentPath = "";

  for (let i = 0; i < segments.length; i++) {
    const segment = segments[i];
    currentPath += `/${ segment }`;

    if (segment === "dashboard") continue;

    const isDynamicSegment =
      segment.match(/^[0-9a-fA-F-]+$/) ||
      (segment.length > 20 && !routeLabels[segment]);

    const label = isDynamicSegment ? "Детали" : routeLabels[segment] || segment;

    breadcrumbs.push({
      label,
      href: currentPath,
      isLast: i === segments.length - 1,
      isHome: false,
    });
  }

  return breadcrumbs;
}

export function SiteBreadcrumb() {
  const pathname = usePathname();
  const items = generateBreadcrumbs(pathname);

  return (
    <Breadcrumb>
      <BreadcrumbList>
        { items.map((item, index) => (
          <React.Fragment key={ item.href }>
            <BreadcrumbItem>
              { item.isLast ? (
                <BreadcrumbPage className="flex items-center">
                  { item.isHome && <Home className="mr-1 inline h-4 w-4"/> }
                  { item.label }
                </BreadcrumbPage>
              ) : (
                <BreadcrumbLink asChild>
                  <Link href={ item.href } className="flex items-center">
                    { item.isHome ? (
                      <Home className="h-4 w-4"/>
                    ) : (
                      item.label
                    ) }
                  </Link>
                </BreadcrumbLink>
              ) }
            </BreadcrumbItem>
            { index < items.length - 1 && <BreadcrumbSeparator/> }
          </React.Fragment>
        )) }
      </BreadcrumbList>
    </Breadcrumb>
  );
}
