import React from "react";
import Sidebar from '@/app/(account)/sidebar';
import Header from '@/app/(account)/header';
import { onboardingApiService } from '@/services/onboarding-api-service';
import { redirect } from 'next/navigation';

interface Props {
  children: React.ReactNode
}

export default async function AccountLayout({ children }: Readonly<Props>) {

  const { status } = await onboardingApiService.onboardingStatus();
  if (status === 'UNCOMPLETED') {
    redirect("/onboarding");
  }

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