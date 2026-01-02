import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Semester, SemesterService } from './semester.service';

export interface University {
  id: number;
  name: string;
  currentSemester: Semester;
}

@Injectable({
  providedIn: 'root',
})
export class UniversityService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/universities';

  static universityMapper = (university: University): University => ({
    ...university,
    currentSemester: SemesterService.semesterMapper(university.currentSemester),
  });

  getUniversitys(): Observable<University[]> {
    return this.http
      .get<University[]>(`${this.baseUrl}`, { withCredentials: true })
      .pipe(map((universities) => universities.map(UniversityService.universityMapper)));
  }

  createUniversity(university: Omit<University, 'id'>): Observable<University> {
    return this.http
      .post<University>(`${this.baseUrl}`, university, { withCredentials: true })
      .pipe(map(UniversityService.universityMapper));
  }

  getUniversity(universityId: number): Observable<University> {
    return this.http
      .get<University>(`${this.baseUrl}/${universityId}`, { withCredentials: true })
      .pipe(map(UniversityService.universityMapper));
  }

  updateUniversity(universityId: number, university: University): Observable<University> {
    return this.http
      .put<University>(`${this.baseUrl}/${universityId}`, university, { withCredentials: true })
      .pipe(map(UniversityService.universityMapper));
  }

  deleteUniversity(universityId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${universityId}`, { withCredentials: true });
  }
}
