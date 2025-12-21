import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Course } from './course.service';

interface CourseMaterial {
  id: number;
  course: Course;
  title: string;
  url: string;
}

@Injectable({
  providedIn: 'root',
})
export class CourseMaterialService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/course-materials';

  getMaterials(): Observable<CourseMaterial[]> {
    return this.http.get<CourseMaterial[]>(`${this.baseUrl}`, { withCredentials: true });
  }

  createMaterial(material: Omit<CourseMaterial, 'id'>): Observable<CourseMaterial> {
    return this.http.post<CourseMaterial>(`${this.baseUrl}`, material, {
      withCredentials: true,
    });
  }

  getMaterial(materialId: number): Observable<CourseMaterial> {
    return this.http.get<CourseMaterial>(`${this.baseUrl}/${materialId}`, {
      withCredentials: true,
    });
  }

  updateMaterial(materialId: number, material: CourseMaterial): Observable<CourseMaterial> {
    return this.http.put<CourseMaterial>(`${this.baseUrl}/${materialId}`, material, {
      withCredentials: true,
    });
  }

  deleteMaterial(materialId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${materialId}`, {
      withCredentials: true,
    });
  }
}
