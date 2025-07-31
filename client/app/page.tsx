import Image from "next/image";
import Link from "next/link";
import {Wallet} from "lucide-react";
import {Button} from "@/components/ui/button";

export default function Home() {
  return (
    <div className="flex flex-col min-h-screen">
      <header className="sticky top-0 z-1 w-full">
        <div className="container h-18 mx-auto flex items-center">
          <Link href="/">
            <Wallet className="h-9 w-9"/>
          </Link>
        </div>
      </header>
      <main className="flex flex-1">
        <div className="container mx-auto">
          <section className="pt-20">
            <div className="flex flex-row">
              <div>
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
                <Button asChild size="xl" className="text-lg">
                  <Link href="/">Войдите в свой аккаунт</Link>
                </Button>
              </div>
              <Image src="/investments.svg" alt="investments" width={ 640 } height={ 640 }/>
            </div>
          </section>
        </div>
      </main>
    </div>
  );
}
