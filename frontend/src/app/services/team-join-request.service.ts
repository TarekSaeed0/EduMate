import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TeamJoinStatus } from './team.service';

export interface TeamJoinRequest {
  id?: number;
  teamId: number;
  studentId: number;
  status: TeamJoinStatus;
}

@Injectable({ providedIn: 'root' })
export class TeamJoinRequestService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/team-join-requests';

  createRequest(request: TeamJoinRequest): Observable<TeamJoinRequest> {
    // NUCLEAR FIX: Manually construct the object to ensure NO extra fields exist
    const payload = {
      teamId: request.teamId,
      studentId: request.studentId,
      status: request.status
    };
    return this.http.post<TeamJoinRequest>(this.baseUrl, payload, { withCredentials: true });
  }

  getRequests(filter?: any): Observable<TeamJoinRequest[]> {
    let params = new HttpParams();
    if (filter) {
      Object.entries(filter).forEach(([key, value]) => {
        if (value !== null && value !== undefined) {
          params = params.append(key, value.toString());
        }
      });
    }
    return this.http.get<TeamJoinRequest[]>(this.baseUrl, { withCredentials: true, params });
  }

  acceptRequest(requestId: number): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/${requestId}/accept`, {}, { withCredentials: true });
  }

  rejectRequest(requestId: number): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/${requestId}/reject`, {}, { withCredentials: true });
  }
}
