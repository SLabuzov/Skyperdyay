import { UUID } from "crypto";
import React from "react";

interface Props {
  id: UUID
}

export default async function WalletPage(props: { params: Promise<Props> }) {
  const params = await props.params;
  const id = params.id;

  return (
    <div className="flex flex-col flex-1 gap-4 px-8">
      <div className="flex items-center justify-between h-10">
        <h1 className="text-base font-bold text-gray-600">Детальная информация по кошельку</h1>
      </div>
      <div className="flex flex-col text-primary w-sm">
        ID: { id }
      </div>
    </div>
  );
}
