import React from 'react';

interface Props {
  children: React.ReactNode
}

export default function OnboardingLayout({ children }: Readonly<Props>) {
  return (
    <div className="container mx-auto flex h-screen">
      <div className="flex flex-1 flex-col items-center">
        { children }
      </div>
    </div>
  );
}