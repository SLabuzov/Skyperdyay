import React from "react";
import { SignedIn, UserButton } from "@clerk/nextjs";
import { SiteBreadcrumb } from "@/components/site-breadcrumb";

export default function Header() {
  return (
    <header className="flex bg-header-background min-h-18 h-18 px-8">
      <div className="flex flex-1 items-center justify-between">
        <nav className="flex items-center">
          <SiteBreadcrumb/>
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