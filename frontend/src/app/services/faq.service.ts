import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface FAQ {
  question: string;
  answer: string;
  categories: string[];
}

@Injectable({
  providedIn: 'root',
})
export class FAQService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/faqs';

  getFAQs(question?: string, answer?: string, categories?: string[]): Observable<FAQ[]> {
    return this.http.get<FAQ[]>(`${this.baseUrl}`, {
      withCredentials: true,
      params: {
        ...(question && { question }),
        ...(answer && { answer }),
        ...(categories && { categories: categories.join(',') }),
      },
    });
  }

  getFAQ(id: number): Observable<FAQ> {
    return this.http.get<FAQ>(`${this.baseUrl}/${id}`, { withCredentials: true });
  }

  getCategories(): Observable<string[]> {
    return this.http.get<string[]>(`${this.baseUrl}/categories`, { withCredentials: true });
  }
}
