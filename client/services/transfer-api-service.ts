import { CreateTransferDto } from "@/lib/types";
import { apiClient } from "@/services/api-client";

class TransferApiService {
  private readonly baseUrl: string = process.env.BACKEND_APP_API_ENDPOINT! + '/funds-transfers';

  async createTransfer(createTransferDto: CreateTransferDto): Promise<void> {
    return apiClient.post<CreateTransferDto>(this.baseUrl, createTransferDto);
  }
}

export const transferApiService = new TransferApiService();
