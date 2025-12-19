import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';

export interface TimePeriod {
  id: number;
  startTime: Date;
  endTime: Date;
}

@Injectable({
  providedIn: 'root',
})
export class TimePeriodService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/time-periods';

  static periodMapper = (period: TimePeriod): TimePeriod => ({
    ...period,
    startTime: new Date(period.startTime),
    endTime: new Date(period.endTime),
  });

  getPeriods(): Observable<TimePeriod[]> {
    return this.http
      .get<TimePeriod[]>(`${this.baseUrl}`, { withCredentials: true })
      .pipe(map((periods) => periods.map(TimePeriodService.periodMapper)));
  }

  createPeriod(period: Omit<TimePeriod, 'id'>): Observable<TimePeriod> {
    return this.http
      .post<TimePeriod>(`${this.baseUrl}`, period, {
        withCredentials: true,
      })
      .pipe(map(TimePeriodService.periodMapper));
  }

  getPeriod(periodId: number): Observable<TimePeriod> {
    return this.http
      .get<TimePeriod>(`${this.baseUrl}/${periodId}`, {
        withCredentials: true,
      })
      .pipe(map(TimePeriodService.periodMapper));
  }

  updatePeriod(periodId: number, period: TimePeriod): Observable<TimePeriod> {
    return this.http
      .put<TimePeriod>(`${this.baseUrl}/${periodId}`, period, {
        withCredentials: true,
      })
      .pipe(map(TimePeriodService.periodMapper));
  }

  deletePeriod(periodId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${periodId}`, {
      withCredentials: true,
    });
  }
}
