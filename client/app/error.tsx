'use client'

import React, { useEffect } from 'react'
import Link from 'next/link';
import { Button } from '@/components/ui/button'
import { OctagonX, RefreshCw } from 'lucide-react'

interface Props {
  error: Error & { digest?: string }
  reset: () => void
}

export default function RootErrorBoundary({ error, reset }: Props) {
  useEffect(() => {
    console.error('Root error boundary caught:', error)
  }, [error])

  return (
    <div className="min-h-screen flex items-center justify-center bg-background px-4">
      <div className="max-w-lg w-full text-center">
        <div className="flex gap-3 p-6 mb-6 text-sm border-1 border-stroke-error-weak bg-fill-error-weak rounded-sm">
          <div className="flex items-center text-icon-error h-7">
            <OctagonX/>
          </div>
          <div className="flex flex-col flex-1 gap-1 justify-items-start">
            <h4 className="text-left text-xl font-semibold text-strong">Произошла ошибка</h4>
            <p className="text-left text-base font-normal text-weak">К сожалению, что-то пошло не так. Попробуйте
              обновить страницу</p>
          </div>
        </div>
        <div className="flex flex-col sm:flex-row gap-3 justify-center">
          <Button onClick={ reset } variant="default" className="gap-2">
            <RefreshCw className="w-4 h-4"/>
            Попробовать снова
          </Button>
          <Button variant="outline" asChild>
            <Link href="/">На главную</Link>
          </Button>
        </div>

        { process.env.NODE_ENV === 'development' && (
          <div className="mt-8 p-4 bg-gray-100 rounded-lg text-left overflow-auto">
            <p className="text-xs font-mono text-gray-600 mb-2">Error digest: { error.digest }</p>
            <pre className="text-xs font-mono text-red-600 whitespace-pre-wrap">
              { error.message }
            </pre>
          </div>
        ) }
      </div>
    </div>
  )
}
