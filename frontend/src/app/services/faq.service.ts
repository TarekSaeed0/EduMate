import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface FAQ {
  question: string;
  answer: string;
  categories: string[];
}

export interface FAQFilter {
  question?: string;
  answer?: string;
  categories?: string[];
}

@Injectable({
  providedIn: 'root',
})
export class FAQService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/faqs';

  getFAQs(filter?: FAQFilter): Observable<FAQ[]> {
    let params = new HttpParams();

    if (filter) {
      Object.entries(filter).forEach(([key, value]) => {
        if (Array.isArray(value)) {
          params = params.append(key, value.join(','));
        } else if (value !== undefined) {
          params = params.append(key, value);
        }
      });
    }

    return this.http.get<FAQ[]>(`${this.baseUrl}`, {
      withCredentials: true,
      params,
    });
  }

  createFaq(faq: Omit<FAQ, 'id'>): Observable<FAQ> {
    return this.http.post<FAQ>(`${this.baseUrl}`, faq, { withCredentials: true });
  }

  getFAQ(faqId: number): Observable<FAQ> {
    return this.http.get<FAQ>(`${this.baseUrl}/${faqId}`, { withCredentials: true });
  }

  updateFAQ(faqId: number, faq: FAQ): Observable<FAQ> {
    return this.http.put<FAQ>(`${this.baseUrl}/${faqId}`, faq, { withCredentials: true });
  }

  getCategories(): Observable<string[]> {
    return this.http.get<string[]>(`${this.baseUrl}/categories`, { withCredentials: true });
  }
}
