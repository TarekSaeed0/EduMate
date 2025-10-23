import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthenticationResponse, AuthenticationService } from '../services/authentication.service';
import { catchError, switchMap, throwError } from 'rxjs';

export const authenticationInterceptor: HttpInterceptorFn = (req, next) => {
  if (req.url.includes('/api/auth')) {
    return next(req);
  }

  const authenticationService = inject(AuthenticationService);
  const accessToken = authenticationService.getAccessToken();

  let clonedReq = req;
  if (accessToken) {
    clonedReq = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${accessToken}`),
    });
  }
  return next(req).pipe(
    catchError((error) => {
      if (error.status === 401) {
        return authenticationService.refresh().pipe(
          switchMap(({ accessToken }: AuthenticationResponse) => {
            return next(
              req.clone({
                headers: req.headers.set('Authorization', `Bearer ${accessToken}`),
              }),
            );
          }),
          catchError((error) => {
            authenticationService.signout();
            return throwError(() => error);
          }),
        );
      }
      return throwError(() => error);
    }),
  );
};
