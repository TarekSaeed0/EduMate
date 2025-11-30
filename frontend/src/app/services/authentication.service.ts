import { HttpClient } from '@angular/common/http';
import { computed, inject, Injectable, signal } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { Student } from './student.service';

export enum Gender {
  Male = 'MALE',
  Female = 'FEMALE',
}

export interface SignupRequest {
  studentId: number;
  name: string;
  gender: Gender;
  email: string;
  universityEmail: string;
  password: string;
}

export interface SigninRequest {
  email: string;
  password: string;
}

export interface User {
  id: number;
  email: string;
  roles: string[];
  student: Student;
}

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/auth';

  user = signal<User | null>(null);
  isSignedIn = computed(() => this.user() !== null);

  loadUser(): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/me`, { withCredentials: true }).pipe(
      tap({
        next: (user) => this.user.set(user),
        error: () => this.user.set(null),
      }),
    );
  }

  signup(request: SignupRequest): Observable<Object> {
    return this.http.post(`${this.baseUrl}/signup`, request);
  }

  signin(request: SigninRequest): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/signin`, request, { withCredentials: true }).pipe(
      tap(() => {
        this.loadUser().subscribe();
      }),
    );
  }

  signout(): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/signout`, {}, { withCredentials: true }).pipe(
      tap(() => {
        this.user.set(null);
      }),
    );
  }
}
