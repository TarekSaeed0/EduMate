import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';

export interface Announcement {
  id: number;
  scopeType: string;
  scopeId: number;
  scope?: { [key: string]: any }; // Optional to prevent mapping errors
  title: string;
  content: string;
  createdAt: Date;
}

@Injectable({
  providedIn: 'root',
})
export class AnnouncementService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/announcements';

  static announcementMapper = (announcement: any): Announcement => ({
    ...announcement,
    createdAt: new Date(announcement.createdAt),
  });

  getAnnouncements(filter?: any): Observable<Announcement[]> {
    return this.http
      .get<Announcement[]>(`${this.baseUrl}`, { withCredentials: true })
      .pipe(map((announcements) => announcements.map(AnnouncementService.announcementMapper)));
  }

  createAnnouncement(announcement: any): Observable<Announcement> {
    return this.http
      .post<Announcement>(`${this.baseUrl}`, announcement, { withCredentials: true })
      .pipe(map(AnnouncementService.announcementMapper));
  }

  updateAnnouncement(announcementId: number, announcement: any): Observable<Announcement> {
    console.log("Payload sent to server:", announcement); // Debugging line
    return this.http
      .put<Announcement>(`${this.baseUrl}/${announcementId}`, announcement, { withCredentials: true })
      .pipe(map(AnnouncementService.announcementMapper));
  }

  deleteAnnouncement(announcementId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${announcementId}`, { withCredentials: true });
  }
}
