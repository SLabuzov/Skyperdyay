import { auth } from '@clerk/nextjs/server';
import { unauthorized } from 'next/navigation';

class ApiClient {

  private async getAccessToken(): Promise<string | null> {
    const { getToken } = await auth();
    return await getToken();
  }

  async get<T>(endpoint: string): Promise<T> {
    const accessToken = await this.getAccessToken();
    if (!accessToken) {
      unauthorized();
    }

    const response = await fetch(endpoint, {
      headers: {
        Authorization: `Bearer ${ accessToken }`,
        "Content-Type": "application/json"
      }
    });

    const json = await response.json();

    return json as Promise<T>;
  }
}

export const apiClient = new ApiClient();