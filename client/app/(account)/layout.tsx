import React from "react";
import Sidebar from '@/app/(account)/sidebar';
import Header from '@/app/(account)/header';

interface Props {
  children: React.ReactNode
}

export default function AccountLayout({ children }: Readonly<Props>) {
  return (
    <div className="flex h-screen">
      <Sidebar/>
      <div className="flex-1 flex flex-col overflow-hidden">
        <Header/>
        { children }
      </div>
    </div>
  );
}
