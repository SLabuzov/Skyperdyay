import Link from "next/link";
import { Wallet } from "lucide-react";
import React from "react";

export default function Logo() {
  return (
    <div className="flex items-center h-18 px-8">
      <Link href="/">
        <Wallet className="h-9 w-9 text-primary"/>
      </Link>
    </div>
  );
}