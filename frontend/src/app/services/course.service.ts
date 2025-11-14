import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface Course {
  id: number;
  code: string;
  name: string;
  credits: number;
}

@Injectable({
  providedIn: 'root',
})
export class CourseService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/courses';

  getCourses(): Observable<Course[]> {
    return this.http.get<Course[]>(`${this.baseUrl}`, { withCredentials: true });
  }

  createCourse(course: Omit<Course, 'id'>): Observable<Course> {
    return this.http.post<Course>(`${this.baseUrl}`, course, { withCredentials: true });
  }

  getCourse(id: number): Observable<Course> {
    return this.http.get<Course>(`${this.baseUrl}/${id}`, { withCredentials: true });
  }

  updateCourse(id: number, course: Omit<Course, 'id'>): Observable<Course> {
    return this.http.put<Course>(`${this.baseUrl}/${id}`, course, { withCredentials: true });
  }

  deleteCourse(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`, { withCredentials: true });
  }
}
