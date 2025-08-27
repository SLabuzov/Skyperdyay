import React from "react";
import Sidebar from '@/app/(account)/sidebar';
import Header from '@/app/(account)/header';

interface Props {
  children: React.ReactNode
}

export default function AccountLayout({ children }: Readonly<Props>) {
  return (
    <div className="container mx-auto flex h-screen">
      <Sidebar/>
      <div className="flex flex-1 flex-col">
        <Header/>
        { children }
      </div>
    </div>
  );
}