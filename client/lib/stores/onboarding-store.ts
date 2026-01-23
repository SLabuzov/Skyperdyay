import { create } from 'zustand';
import { persist } from 'zustand/middleware';
import { Country, CreateCategoryDto, CreateWalletDto, Currency, Timezone } from '@/lib/types';

export interface OnboardingStore {
  currentStep: number;
  profile: {
    country: Country | null;
    timezone: Timezone | null;
    currency: Currency | null;
  };
  incomeCategories: CreateCategoryDto[];
  expenseCategories: CreateCategoryDto[];
  wallet: CreateWalletDto;
  acceptedTerms: boolean;
  isSubmitting: boolean;
  errors: Record<string, string>;

  // Methods
  setCountry: (country: Country) => void;
  setTimezone: (timezone: Timezone) => void;
  setCurrency: (currency: Currency) => void;
  setIncomeCategories: (categories: CreateCategoryDto[]) => void;
  setExpenseCategories: (categories: CreateCategoryDto[]) => void;
  setWallet: (wallet: CreateWalletDto) => void;
  setAcceptedTerms: (accepted: boolean) => void;
  setCurrentStep: (step: number) => void;
  setError: (field: string, error: string | null) => void;
  validateStep: (step: number) => boolean;
  reset: () => void;
  setIsSubmitting: (isSubmitting: boolean) => void;
}

const defaultState = {
  currentStep: 1,
  profile: {
    country: null,
    timezone: null,
    currency: null,
  },
  incomeCategories: [],
  expenseCategories: [],
  wallet: {
    walletName: '',
    currencyCode: '',
    balance: 0
  },
  acceptedTerms: false,
  isSubmitting: false,
  errors: {},
};

export const useOnboardingStore = create<OnboardingStore>()(
  persist(
    (set, get) => ({
      ...defaultState,

      setCountry: (country) => {
        set((state) => ({
          profile: { ...state.profile, country },
          errors: {},
        }));
        // Auto-set default timezone and currency when country changes
        if (country.defaultTimezone) {
          set((state) => ({
            profile: { ...state.profile, timezone: country.defaultTimezone },
          }));
        }
        if (country.defaultCurrency) {
          set((state) => ({
            profile: { ...state.profile, currency: country.defaultCurrency },
            wallet: { ...state.wallet, currencyCode: country.defaultCurrency.code },
          }));
        }
      },

      setTimezone: (timezone) => set((state) => ({
        profile: { ...state.profile, timezone },
        errors: {},
      })),

      setCurrency: (currency) => set((state) => ({
        profile: { ...state.profile, currency },
        errors: {},
      })),

      setIncomeCategories: (categories) => set({ incomeCategories: categories, errors: {} }),

      setExpenseCategories: (categories) => set({ expenseCategories: categories, errors: {} }),

      setWallet: (wallet) => set({ wallet, errors: {} }),

      setAcceptedTerms: (accepted) => set({ acceptedTerms: accepted }),

      setCurrentStep: (step) => set({ currentStep: step }),

      setError: (field, error) => {
        const errors = { ...get().errors };
        if (error) {
          errors[field] = error;
        } else {
          delete errors[field];
        }
        set({ errors });
      },

      validateStep: (step) => {
        const state = get();
        switch (step) {
          case 1:
            return state.profile.country !== null;
          case 2:
            return state.profile.timezone !== null;
          case 3:
            return state.profile.currency !== null;
          case 4:
            return state.incomeCategories.length > 0;
          case 5:
            return state.expenseCategories.length > 0;
          case 6:
            return state.wallet !== null &&
              state.wallet.walletName.length > 0 &&
              state.wallet.currencyCode.length > 0;
          case 7:
            return state.acceptedTerms;
          default:
            return false;
        }
      },

      setIsSubmitting: (isSubmitting) => set({ isSubmitting }),

      reset: () => set(defaultState),
    }),
    {
      name: 'onboarding-storage',
    }
  )
);
