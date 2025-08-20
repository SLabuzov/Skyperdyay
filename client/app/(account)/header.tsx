import { SignedIn, UserButton } from "@clerk/nextjs";
import React from "react";
import Link from "next/link";

export default function Header() {
  return (
    <header className="bg-header-background px-8 py-5">
      <div className="flex items-center justify-between">
        <nav className="flex items-center space-y-2">
          <Link href={"/account/dashboard"}>
            <p className="text-xl font-bold text-text-strong text-[#1E4841]">Кошельки</p>
          </Link>
        </nav>
        <div className="flex items-center">
          <SignedIn>
            <UserButton/>
          </SignedIn>
        </div>
      </div>
    </header>
  );
}
