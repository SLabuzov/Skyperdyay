import { CreateExpenseTransactionDto, CreateIncomeTransactionDto } from '@/lib/types';
import { apiClient } from '@/services/api-client';

class TransactionApiService {
  private readonly baseUrl: string = process.env.BACKEND_APP_API_ENDPOINT! + '/transactions';

  async withdraw(newExpenseTransaction: CreateExpenseTransactionDto): Promise<void> {
    return apiClient.post<CreateExpenseTransactionDto>(this.baseUrl + '/expense', newExpenseTransaction);
  }

  async topUp(newIncomeTransaction: CreateIncomeTransactionDto): Promise<void> {
    return apiClient.post<CreateIncomeTransactionDto>(this.baseUrl + '/income', newIncomeTransaction);
  }
}

export const transactionApiService = new TransactionApiService();
