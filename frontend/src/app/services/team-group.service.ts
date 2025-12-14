import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CourseOffering } from './course-offering.service';

export interface TeamGroup {
  id: number;
  offering: CourseOffering;
  name: string;
  minimumMemberCount: number;
  maximumMemberCount: number;
}

@Injectable({
  providedIn: 'root',
})
export class TeamGroupService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/team-groups';

  getGroups(): Observable<TeamGroup[]> {
    return this.http.get<TeamGroup[]>(`${this.baseUrl}`, { withCredentials: true });
  }

  createGroup(group: Omit<TeamGroup, 'id'>): Observable<TeamGroup> {
    return this.http.post<TeamGroup>(`${this.baseUrl}`, group, { withCredentials: true });
  }

  getGroup(groupId: number): Observable<TeamGroup> {
    return this.http.get<TeamGroup>(`${this.baseUrl}/${groupId}`, { withCredentials: true });
  }

  updateGroup(groupId: number, group: TeamGroup): Observable<TeamGroup> {
    return this.http.put<TeamGroup>(`${this.baseUrl}/${groupId}`, group, { withCredentials: true });
  }

  deleteGroup(groupId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${groupId}`, { withCredentials: true });
  }
}
