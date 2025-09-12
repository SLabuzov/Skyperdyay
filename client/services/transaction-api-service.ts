import { CreateExpenseTransactionDto } from '@/lib/types';
import { apiClient } from '@/services/api-client';

class TransactionApiService {
  private readonly baseUrl: string = process.env.BACKEND_APP_API_ENDPOINT! + '/wallets';

  async withdraw(newExpenseTransaction: CreateExpenseTransactionDto): Promise<void> {
    return apiClient.post<CreateExpenseTransactionDto>(this.baseUrl + '/expense', newExpenseTransaction);
  }
}

export const transactionApiService = new TransactionApiService();
