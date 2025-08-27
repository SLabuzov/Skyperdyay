import Image from "next/image";
import Link from "next/link";
import { SignedIn, SignedOut, SignInButton, UserButton } from "@clerk/nextjs";
import { Button } from "@/components/ui/button";
import React from "react";
import Logo from "@/components/logo";

export default function Home() {
  return (
    <div className="flex flex-col min-h-screen">
      <header className="sticky top-0 z-1 w-full bg-white">
        <div className="container mx-auto flex items-center justify-between">
          <Logo/>
          <div className="flex items-center px-8">
            <SignedOut>
              <Button asChild>
                <SignInButton>Войдите в свой аккаунт</SignInButton>
              </Button>
            </SignedOut>
            <SignedIn>
              <UserButton/>
            </SignedIn>
          </div>
        </div>
      </header>
      <main className="flex flex-1">
        <div className="container mx-auto px-8">
          <section className="pt-20">
            <div className="flex flex-row">
              <div className="text-primary">
                <h1 className="text-6xl font-bold mb-12">
                  Возьмите под контроль свои семейные финансы
                </h1>
                <p className="text-xl/8 mb-12">
                  Получайте информацию о своих расходах в режиме реального времени,
                  ставьте персонализированные цели сбережений и отслеживайте свой прогресс
                  на пути к долгосрочному богатству — все на одной интуитивно понятной
                  платформе.<br/>
                  Разработано для семей, которые хотят стать умнее, вместе экономить и обрести
                  прочную финансовую
                  свободу.<br/>
                  Отмечайте важные вехи, будьте ответственны и воплощайте общие мечты в
                  реальность.
                </p>
                <SignedOut>
                  <Button asChild size="xl" className="text-lg">
                    <SignInButton>Войдите в свой аккаунт</SignInButton>
                  </Button>
                </SignedOut>
                <SignedIn>
                  <Button asChild size="xl" className="text-lg">
                    <Link href={ "/dashboard" }>
                      Перейти к аналитике
                    </Link>
                  </Button>
                </SignedIn>
              </div>
              <Image src="/investments.svg" alt="investments" width={ 640 } height={ 640 }/>
            </div>
          </section>
        </div>
      </main>
    </div>
  );
}
