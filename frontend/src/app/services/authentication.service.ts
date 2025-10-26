import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Observable, tap } from 'rxjs';

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
  role: 'STUDENT' | 'COORDINATOR';
}

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/auth';

  user = signal<User | null>(null);

  constructor() {
    this.fetchUser();
  }

  fetchUser() {
    this.http.get<User>(`${this.baseUrl}/me`, { withCredentials: true }).subscribe({
      next: (user) => this.user.set(user),
      error: () => this.user.set(null),
    });
  }

  signup(request: SignupRequest): Observable<Object> {
    return this.http.post(`${this.baseUrl}/signup`, request);
  }

  signin(request: SigninRequest): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/signin`, request, { withCredentials: true }).pipe(
      tap(() => {
        this.fetchUser();
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

  isSignedIn(): boolean {
    return this.user() !== null;
  }
}
