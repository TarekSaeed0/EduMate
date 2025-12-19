import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Student } from './student.service';
import { Team, TeamJoinStatus } from './team.service';
import { Observable } from 'rxjs';

export interface TeamJoinRequest {
  id: number;
  team: Team;
  student: Student;
  status: TeamJoinStatus;
}

export interface TeamJoinRequestFilter {
  teamId?: number;
  studentId?: number;
  status?: TeamJoinStatus;
}

@Injectable({
  providedIn: 'root',
})
export class TeamJoinRequestService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/team-join-requests';

  getRequests(filter?: TeamJoinRequestFilter): Observable<TeamJoinRequest[]> {
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

    return this.http.get<TeamJoinRequest[]>(`${this.baseUrl}`, { withCredentials: true, params });
  }

  createRequest(request: Omit<TeamJoinRequest, 'id'>): Observable<TeamJoinRequest> {
    return this.http.post<TeamJoinRequest>(`${this.baseUrl}`, request, { withCredentials: true });
  }

  getRequest(requestId: number): Observable<TeamJoinRequest> {
    return this.http.get<TeamJoinRequest>(`${this.baseUrl}/${requestId}`, {
      withCredentials: true,
    });
  }

  acceptRequest(requestId: number): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/${requestId}/accept`, { withCredentials: true });
  }

  rejectRequest(requestId: number): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/${requestId}/reject`, { withCredentials: true });
  }

  deleteRequest(requestId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${requestId}`, { withCredentials: true });
  }
}
