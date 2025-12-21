import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { CourseOffering } from './course-offering.service';
import { TimeSlot, TimeSlotService } from './time-slot.service';

export type CourseSessionType = 'LECTURE' | 'TUTORIAL' | 'LAB';

export interface CourseSession {
  id: number;
  offering: CourseOffering;
  slot: TimeSlot;
  location: string;
  type: CourseSessionType;
}

@Injectable({
  providedIn: 'root',
})
export class CourseSessionService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/sessions';

  static sessionMapper = (session: CourseSession): CourseSession => ({
    ...session,
    slot: TimeSlotService.slotMapper(session.slot),
  });

  getSessions(): Observable<CourseSession[]> {
    return this.http
      .get<CourseSession[]>(`${this.baseUrl}`, { withCredentials: true })
      .pipe(map((sessions) => sessions.map(CourseSessionService.sessionMapper)));
  }

  createSession(session: Omit<CourseSession, 'id'>): Observable<CourseSession> {
    return this.http
      .post<CourseSession>(`${this.baseUrl}`, session, { withCredentials: true })
      .pipe(map(CourseSessionService.sessionMapper));
  }

  getSession(sessionId: number): Observable<CourseSession> {
    return this.http
      .get<CourseSession>(`${this.baseUrl}/${sessionId}`, { withCredentials: true })
      .pipe(map(CourseSessionService.sessionMapper));
  }

  updateSession(sessionId: number, session: CourseSession): Observable<CourseSession> {
    return this.http
      .put<CourseSession>(`${this.baseUrl}/${sessionId}`, session, {
        withCredentials: true,
      })
      .pipe(map(CourseSessionService.sessionMapper));
  }

  deleteSession(sessionId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${sessionId}`, { withCredentials: true });
  }
}
