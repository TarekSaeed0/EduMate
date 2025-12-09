import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Course } from './course.service';

export interface CourseOffering {
  id: number;
  semesterId: number;
  course: Course;
}

interface CourseOfferingFilter {
  semesterId?: number;
  courseId?: number;
}

@Injectable({
  providedIn: 'root',
})
export class CourseOfferingService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/offerings';

  getOfferings(filter?: CourseOfferingFilter): Observable<CourseOffering[]> {
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

    return this.http.get<CourseOffering[]>(`${this.baseUrl}`, {
      withCredentials: true,
      params,
    });
  }

  createOffering(offering: Omit<CourseOffering, 'id'>): Observable<CourseOffering> {
    return this.http.post<CourseOffering>(`${this.baseUrl}`, offering, {
      withCredentials: true,
    });
  }

  getOffering(offeringId: number): Observable<CourseOffering> {
    return this.http.get<CourseOffering>(`${this.baseUrl}/${offeringId}`, {
      withCredentials: true,
    });
  }

  deleteOffering(offeringId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${offeringId}`, {
      withCredentials: true,
    });
  }
}
