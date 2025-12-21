import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Student } from './student.service';
import { Observable } from 'rxjs';
import { TeamGroup } from './team-group.service';

export interface Team {
  id: number;
  group: TeamGroup;
  leader: Student;
  members: Student[];
}

export type TeamStatus = 'INCOMPLETE' | 'SUFFICIENT' | 'COMPLETE';

export interface TeamFilter {
  groupId?: number;
  leaderId?: number;
  memberId?: number;
  status?: TeamStatus;
}

export type TeamJoinStatus = 'PENDING' | 'ACCEPTED' | 'REJECTED';

@Injectable({
  providedIn: 'root',
})
export class TeamService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/teams';

  getTeams(filter?: TeamFilter): Observable<Team[]> {
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

    return this.http.get<Team[]>(this.baseUrl, { withCredentials: true, params });
  }

  // Used for "Create Team as Leader" button
  createTeam(team: Omit<Team, 'id'>): Observable<Team> {
    return this.http.post<Team>(this.baseUrl, team, { withCredentials: true });
  }


  getTeam(id: number): Observable<Team> {
    return this.http.get<Team>(`${this.baseUrl}/${id}`, { withCredentials: true });
  }

  deleteTeam(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`, { withCredentials: true });
  }


}
