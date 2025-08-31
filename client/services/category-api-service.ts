import { CategoryInfo, CreateCategoryDto } from '@/lib/types';
import { apiClient } from '@/services/api-client';

class CategoryApiService {
  private readonly baseUrl: string = process.env.BACKEND_APP_API_ENDPOINT! + '/categories';

  async getCategories(): Promise<CategoryInfo[]> {
    return apiClient.get<CategoryInfo[]>(this.baseUrl);
  }

  async defineCategory(createCategoryDto: CreateCategoryDto): Promise<void> {
    return apiClient.post<CreateCategoryDto>(this.baseUrl, createCategoryDto);
  }
}

export const categoryApiService = new CategoryApiService();