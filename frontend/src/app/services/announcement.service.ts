import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';

export interface Announcement {
  id: number;
  scopeType: string;
  scopeId: number;
  title: string;
  content: string;
  createdAt: Date;
}

export interface AnnouncementFilter {
  studentId?: number;
}

@Injectable({
  providedIn: 'root',
})
export class AnnouncementService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/announcements';

  static announcementMapper = (announcement: Announcement): Announcement => ({
    ...announcement,
    createdAt: new Date(announcement.createdAt),
  });

  getAnnouncements(filter?: AnnouncementFilter): Observable<Announcement[]> {
    let params = new HttpParams();

    if (filter) {
      Object.entries(filter).forEach(([key, value]) => {
        if (Array.isArray(value)) {
          params = params.append(key, value.join(','));
        } else if (value !== undefined) {
          params = params.append(key, value);
        }
      });
    }

    return this.http
      .get<Announcement[]>(`${this.baseUrl}`, { withCredentials: true, params })
      .pipe(map((announcements) => announcements.map(AnnouncementService.announcementMapper)));
  }

  createAnnouncement(announcement: Omit<Announcement, 'id'>): Observable<Announcement> {
    return this.http
      .post<Announcement>(`${this.baseUrl}`, announcement, { withCredentials: true })
      .pipe(map(AnnouncementService.announcementMapper));
  }

  getAnnouncement(announcementId: number): Observable<Announcement> {
    return this.http
      .get<Announcement>(`${this.baseUrl}/${announcementId}`, {
        withCredentials: true,
      })
      .pipe(map(AnnouncementService.announcementMapper));
  }

  updateAnnouncement(announcementId: number, announcement: Announcement): Observable<Announcement> {
    return this.http
      .put<Announcement>(`${this.baseUrl}/${announcementId}`, announcement, {
        withCredentials: true,
      })
      .pipe(map(AnnouncementService.announcementMapper));
  }

  deleteAnnouncement(announcementId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${announcementId}`, { withCredentials: true });
  }
}
