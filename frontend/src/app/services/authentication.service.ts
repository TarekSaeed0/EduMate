import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/auth';

  signup(
    name: string,
    email: string,
    universityEmail: string,
    studentId: string,
    password: string,
  ): Observable<any> {
    return this.http.post(`${this.baseUrl}/signup`, {
      name,
      email,
      universityEmail,
      studentId,
      password,
    });
  }

  signin(email: string, password: string): Observable<{ token: string }> {
    return this.http.post<{ token: string }>(`${this.baseUrl}/signin`, { email, password }).pipe(
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
