import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';
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

export interface AuthenticationResponse {
  accessToken: string;
  refreshToken: string;
}

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/auth';

  signup(request: SignupRequest): Observable<any> {
    return this.http.post(`${this.baseUrl}/signup`, request);
  }

  signin(request: SigninRequest): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(`${this.baseUrl}/signin`, request).pipe(
      tap((response) => {
        localStorage.setItem('accessToken', response.accessToken);
        localStorage.setItem('refreshToken', response.refreshToken);
      }),
    );
  }

  refresh(): Observable<AuthenticationResponse> {
    const refreshToken = localStorage.getItem('refreshToken');

    return this.http
      .post<AuthenticationResponse>(`${this.baseUrl}/refresh`, {
        refreshToken,
      })
      .pipe(
        tap((response) => {
          localStorage.setItem('accessToken', response.accessToken);
          localStorage.setItem('refreshToken', response.refreshToken);
        }),
      );
  }

  signout() {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
  }

  getAccessToken() {
    return localStorage.getItem('accessToken');
  }

  getRoles(): string[] {
    const accessToken = this.getAccessToken();
    if (!accessToken) {
      return [];
    }

    const decoded = jwtDecode<{ roles: string[] }>(accessToken);

    return decoded.roles;
  }

  isSignedIn() {
    return !!this.getAccessToken();
  }
}
