import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';

export enum Gender {
  Male = 'MALE',
  Female = 'FEMALE',
}

export interface SignupRequest {
  id: number;
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

export interface SigninResponse {
  token: string;
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

  signin(request: SigninRequest): Observable<SigninResponse> {
    return this.http.post<SigninResponse>(`${this.baseUrl}/signin`, request).pipe(
      tap((response) => {
        localStorage.setItem('token', response.token);
      }),
    );
  }

  signout() {
    localStorage.removeItem('token');
  }

  getToken() {
    return localStorage.getItem('token');
  }

  isSignedIn() {
    return !!this.getToken();
  }
}
