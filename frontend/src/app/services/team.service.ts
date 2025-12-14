import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { CourseOffering } from './course-offering.service';
import { Student } from './student.service';
import { Observable } from 'rxjs';

export interface TeamGroup {
  id: number;
  offering: CourseOffering;
  name: string;
  minimumMemberCount: number;
  maximumMemberCount: number;
}

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

    return this.http.get<Team[]>(`${this.baseUrl}`, { withCredentials: true, params });
  }

  createTeam(team: Omit<Team, 'id'>): Observable<Team> {
    return this.http.post<Team>(`${this.baseUrl}`, team, { withCredentials: true });
  }

  getTeam(id: number): Observable<Team> {
    return this.http.get<Team>(`${this.baseUrl}/${id}`, { withCredentials: true });
  }

  deleteTeam(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`, { withCredentials: true });
  }
}
