import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Student } from './student.service';
import { Team, TeamJoinStatus } from './team.service';
import { Observable } from 'rxjs';

export interface TeamJoinInvite {
  id: number;
  team: Team;
  student: Student;
  status: TeamJoinStatus;
}

export interface TeamJoinInviteFilter {
  teamId?: number;
  studentId?: number;
  status?: TeamJoinStatus;
}

@Injectable({
  providedIn: 'root',
})
export class TeamJoinInviteService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/team-join-invites';

  getInvites(filter?: TeamJoinInviteFilter): Observable<TeamJoinInvite[]> {
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

    return this.http.get<TeamJoinInvite[]>(`${this.baseUrl}`, { withCredentials: true, params });
  }

  createInvite(invite: Omit<TeamJoinInvite, 'id'>): Observable<TeamJoinInvite> {
    return this.http.post<TeamJoinInvite>(`${this.baseUrl}`, invite, { withCredentials: true });
  }

  getInvite(inviteId: number): Observable<TeamJoinInvite> {
    return this.http.get<TeamJoinInvite>(`${this.baseUrl}/${inviteId}`, { withCredentials: true });
  }

  acceptInvite(inviteId: number): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/${inviteId}/accept`, { withCredentials: true });
  }

  rejectInvite(inviteId: number): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/${inviteId}/reject`, { withCredentials: true });
  }

  deleteInvite(inviteId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${inviteId}`, { withCredentials: true });
  }
}
