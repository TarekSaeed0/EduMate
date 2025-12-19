import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';

export type Term = 'SPRING' | 'SUMMER' | 'FALL' | 'WINTER';

export interface Semester {
  id: number;
  term: Term;
  year: number;
  startDate: Date;
  endDate: Date;
}

@Injectable({
  providedIn: 'root',
})
export class SemesterService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/semesters';

  static semesterMapper = (semester: Semester): Semester => ({
    ...semester,
    startDate: new Date(semester.startDate),
    endDate: new Date(semester.endDate),
  });

  getSemesters(): Observable<Semester[]> {
    return this.http
      .get<Semester[]>(`${this.baseUrl}`, { withCredentials: true })
      .pipe(map((semesters) => semesters.map(SemesterService.semesterMapper)));
  }

  createSemester(semester: Omit<Semester, 'id'>): Observable<Semester> {
    return this.http
      .post<Semester>(`${this.baseUrl}`, semester, { withCredentials: true })
      .pipe(map(SemesterService.semesterMapper));
  }

  getSemester(semesterId: number): Observable<Semester> {
    return this.http
      .get<Semester>(`${this.baseUrl}/${semesterId}`, { withCredentials: true })
      .pipe(map(SemesterService.semesterMapper));
  }

  updateSemester(semesterId: number, semester: Semester): Observable<Semester> {
    return this.http
      .put<Semester>(`${this.baseUrl}/${semesterId}`, semester, { withCredentials: true })
      .pipe(map(SemesterService.semesterMapper));
  }

  deleteSemester(semesterId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${semesterId}`, { withCredentials: true });
  }
}
