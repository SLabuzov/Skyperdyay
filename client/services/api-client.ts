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

  async post<T>(endpoint: string, data: T): Promise<void> {
    const accessToken = await this.getAccessToken();
    if (!accessToken) {
      unauthorized();
    }

    const response = await fetch(endpoint, {
      method: 'POST',
      headers: {
        Authorization: `Bearer ${ accessToken }`,
        "Content-Type": "application/json"
      },
      body: JSON.stringify(data)
    });

    if (response.status !== 201) {
      const body = await response.json();
      throw new Error(body.error);
    }
  }
}

export const apiClient = new ApiClient();
