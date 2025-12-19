import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { CourseOffering } from './course-offering.service';
import { TimeSlot, TimeSlotService } from './time-slot.service';

export type SessionType = 'LECTURE' | 'TUTORIAL' | 'LAB';

export interface Session {
  id: number;
  offering: CourseOffering;
  slot: TimeSlot;
  type: SessionType;
}

@Injectable({
  providedIn: 'root',
})
export class SessionService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/sessions';

  static sessionMapper = (session: Session): Session => ({
    ...session,
    slot: TimeSlotService.slotMapper(session.slot),
  });

  getSessions(): Observable<Session[]> {
    return this.http
      .get<Session[]>(`${this.baseUrl}`, { withCredentials: true })
      .pipe(map((sessions) => sessions.map(SessionService.sessionMapper)));
  }

  createSession(session: Omit<Session, 'id'>): Observable<Session> {
    return this.http
      .post<Session>(`${this.baseUrl}`, session, { withCredentials: true })
      .pipe(map(SessionService.sessionMapper));
  }

  getSession(sessionId: number): Observable<Session> {
    return this.http
      .get<Session>(`${this.baseUrl}/${sessionId}`, { withCredentials: true })
      .pipe(map(SessionService.sessionMapper));
  }

  updateSession(sessionId: number, session: Session): Observable<Session> {
    return this.http
      .put<Session>(`${this.baseUrl}/${sessionId}`, session, {
        withCredentials: true,
      })
      .pipe(map(SessionService.sessionMapper));
  }

  deleteSession(sessionId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${sessionId}`, { withCredentials: true });
  }
}
