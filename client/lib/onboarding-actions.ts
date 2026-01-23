'use server';

import { z } from "zod";
import { revalidatePath } from "next/cache";
import { redirect } from "next/navigation";
import { onboardingApiService } from "@/services/onboarding-api-service";
import { CreateCategoryDto, CreateWalletDto, SubmitOnboardingDto } from "@/lib/types";
import { dictionaryApiService } from "@/services/dictionary-api-service";

const OnboardingFormSchema = z.object({
  countryCode: z.string().min(1, "Код страны обязателен"),
  timezoneCode: z.string().min(1, "Код часового пояса обязателен"),
  currencyCode: z.string().min(1, "Код валюты обязателен"),
  incomeCategories: z.string().transform((str) => {
    try {
      return JSON.parse(str) as CreateCategoryDto[];
    } catch {
      return [];
    }
  }),
  expenseCategories: z.string().transform((str) => {
    try {
      return JSON.parse(str) as CreateCategoryDto[];
    } catch {
      return [];
    }
  }),
  wallet: z.string().transform((str) => {
    try {
      return JSON.parse(str) as CreateWalletDto;
    } catch {
      return null;
    }
  }),
  acceptedTerms: z.literal('true'),
});

export type State = {
  message?: string | null;
}

export async function submitOnboarding(prevState: State, formData: FormData) {
  const validatedFields = OnboardingFormSchema.safeParse({
    countryCode: formData.get('countryCode'),
    timezoneCode: formData.get('timezoneCode'),
    currencyCode: formData.get('currencyCode'),
    incomeCategories: formData.get('incomeCategories'),
    expenseCategories: formData.get('expenseCategories'),
    wallet: formData.get('wallet'),
    acceptedTerms: formData.get('acceptedTerms'),
  });

  console.log('validatedFields', validatedFields);

  if (!validatedFields.success) {
    return {
      message: 'Пожалуйста, заполните все поля и примите условия'
    } satisfies State;
  }

  const {
    countryCode,
    timezoneCode,
    currencyCode,
    incomeCategories,
    expenseCategories,
    wallet,
    acceptedTerms
  } = validatedFields.data;

  try {
    // Fetch dictionary data to get full objects
    const [countries, timezones, currencies] = await Promise.all([
      dictionaryApiService.getCountries(),
      dictionaryApiService.getTimezones(),
      dictionaryApiService.getCurrencies(),
    ]);

    const country = countries.find(c => c.code === countryCode);
    const timezone = timezones.find(t => t.code === timezoneCode);
    const currency = currencies.find(c => c.code === currencyCode);

    if (!country || !timezone || !currency) {
      return {
        message: 'Не удалось найти выбранные данные в справочниках'
      } satisfies State;
    }

    if (!wallet) {
      return {
        message: 'Данные кошелька не найдены'
      } satisfies State;
    }

    if (incomeCategories.length === 0) {
      return {
        message: 'Необходимо выбрать хотя бы одну категорию доходов'
      } satisfies State;
    }

    if (expenseCategories.length === 0) {
      return {
        message: 'Необходимо выбрать хотя бы одну категорию расходов'
      } satisfies State;
    }

    // Create OnboardingDto
    const onboardingDto: SubmitOnboardingDto = {
      profile: {
        country: country.code,
        timezone: timezoneCode,
        mainCurrencyCode: currencyCode,
        acceptedTerms: !!acceptedTerms
      },
      incomeCategories,
      expenseCategories,
      wallet,
    } satisfies SubmitOnboardingDto;

    await onboardingApiService.submitOnboarding(onboardingDto);


  } catch (error) {
    return {
      message: error instanceof Error ? error.message : 'Не удалось сохранить настройки. Попробуйте еще раз.'
    } satisfies State;
  }

  revalidatePath("/dashboard");
  revalidatePath("/onboarding");
  redirect("/dashboard");
}
