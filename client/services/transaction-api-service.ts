import { CreateExpenseTransactionDto, CreateIncomeTransactionDto, TransactionInfo } from '@/lib/types';
import { apiClient } from '@/services/api-client';

class TransactionApiService {
  private readonly baseUrl: string = process.env.BACKEND_APP_API_ENDPOINT! + '/transactions';

  async withdraw(newExpenseTransaction: CreateExpenseTransactionDto): Promise<void> {
    return apiClient.post<CreateExpenseTransactionDto>(this.baseUrl + '/expense', newExpenseTransaction);
  }

  async topUp(newIncomeTransaction: CreateIncomeTransactionDto): Promise<void> {
    return apiClient.post<CreateIncomeTransactionDto>(this.baseUrl + '/income', newIncomeTransaction);
  }

  async history(startDate: string, endDate: string): Promise<TransactionInfo[]> {
    const url = new URL(this.baseUrl + '/history');
    url.searchParams.append('startDate', String(startDate));
    url.searchParams.append('endDate', String(endDate));

    return apiClient.get<TransactionInfo[]>(url.toString());
  }
}

export const transactionApiService = new TransactionApiService();
