"use client";

import React from 'react';

export default function TermsDocument() {
  return (
    <div className="prose prose-sm max-w-none text-weak">
      <h3 className="text-lg font-bold text-strong mb-4">Условия использования сервиса Skyperdyay</h3>

      <section className="mb-6">
        <h4 className="text-base font-semibold text-strong mb-2">1. Общие положения</h4>
        <p className="text-sm leading-relaxed mb-2">
          Настоящие Условия использования регулируют отношения между вами и сервисом Skyperdyay
          по использованию платформы для управления семейными финансами.
        </p>
        <p className="text-sm leading-relaxed">
          Используя наш сервис, вы соглашаетесь с данными условиями. Если вы не согласны с какими-либо
          условиями, пожалуйста, не используйте сервис.
        </p>
      </section>

      <section className="mb-6">
        <h4 className="text-base font-semibold text-strong mb-2">2. Использование сервиса</h4>
        <p className="text-sm leading-relaxed mb-2">
          Вы обязуетесь использовать сервис только в законных целях и в соответствии с применимым
          законодательством. Вы несете ответственность за всю информацию, которую вы предоставляете
          через сервис.
        </p>
        <p className="text-sm leading-relaxed">
          Мы оставляем за собой право изменять, приостанавливать или прекращать работу сервиса в любое
          время без предварительного уведомления.
        </p>
      </section>

      <section className="mb-6">
        <h4 className="text-base font-semibold text-strong mb-2">3. Конфиденциальность данных</h4>
        <p className="text-sm leading-relaxed mb-2">
          Мы серьезно относимся к защите ваших персональных данных. Вся финансовая информация
          хранится в зашифрованном виде и используется только для предоставления функциональности сервиса.
        </p>
        <p className="text-sm leading-relaxed">
          Мы не передаем ваши данные третьим лицам без вашего явного согласия, за исключением случаев,
          предусмотренных законодательством.
        </p>
      </section>

      <section className="mb-6">
        <h4 className="text-base font-semibold text-strong mb-2">4. Ответственность</h4>
        <p className="text-sm leading-relaxed mb-2">
          Сервис предоставляется "как есть" без каких-либо гарантий. Мы не несем ответственности за
          любые прямые или косвенные убытки, возникшие в результате использования или невозможности
          использования сервиса.
        </p>
        <p className="text-sm leading-relaxed">
          Вы несете полную ответственность за точность вводимых данных и за решения, принимаемые на
          основе информации, предоставляемой сервисом.
        </p>
      </section>

      <section className="mb-6">
        <h4 className="text-base font-semibold text-strong mb-2">5. Изменения условий</h4>
        <p className="text-sm leading-relaxed">
          Мы оставляем за собой право изменять настоящие Условия использования в любое время.
          Существенные изменения будут доведены до вашего сведения через уведомления в сервисе или
          по электронной почте.
        </p>
      </section>

      <section>
        <h4 className="text-base font-semibold text-strong mb-2">6. Контакты</h4>
        <p className="text-sm leading-relaxed">
          По всем вопросам, связанным с использованием сервиса, вы можете обращаться к нам через
          форму обратной связи в приложении.
        </p>
      </section>

      <div className="mt-8 pt-4 border-t border-stroke-weak">
        <p className="text-xs text-weak italic">
          Дата последнего обновления: { new Date().toLocaleDateString('ru-RU') }
        </p>
      </div>
    </div>
  );
}
