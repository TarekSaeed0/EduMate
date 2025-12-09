import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CourseOffering } from './course-offering.service';

export type CourseRegistrationStatus = 'REGISTERED' | 'PASSED' | 'FAILED' | 'DROPPED';

export interface CourseRegistration {
  studentId: number;
  offering: CourseOffering;
  status: CourseRegistrationStatus;
}

interface CourseRegistrationFilter {
  offeringId?: number;
  semesterId?: number;
  courseId?: number;
  studentId?: number;
  status?: CourseRegistrationStatus;
}

@Injectable({
  providedIn: 'root',
})
export class CourseRegistrationService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/registrations';

  getRegistrations(filter?: CourseRegistrationFilter): Observable<CourseRegistration[]> {
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

    return this.http.get<CourseRegistration[]>(`${this.baseUrl}`, {
      withCredentials: true,
      params,
    });
  }

  createRegistration(registration: Omit<CourseRegistration, 'id'>): Observable<CourseRegistration> {
    return this.http.post<CourseRegistration>(`${this.baseUrl}`, registration, {
      withCredentials: true,
    });
  }

  getRegistration(registrationId: number): Observable<CourseRegistration> {
    return this.http.get<CourseRegistration>(`${this.baseUrl}/${registrationId}`, {
      withCredentials: true,
    });
  }

  updateRegistration(
    registrationId: number,
    registration: CourseRegistration,
  ): Observable<CourseRegistration> {
    return this.http.put<CourseRegistration>(`${this.baseUrl}/${registrationId}`, registration, {
      withCredentials: true,
    });
  }

  deleteRegistration(registrationId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${registrationId}`, {
      withCredentials: true,
    });
  }
}
