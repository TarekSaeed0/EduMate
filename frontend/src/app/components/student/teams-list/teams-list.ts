import { Component, OnInit, inject } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router'; // Removed RouterLink
import { Navbar } from '../../navbar/navbar';
import { forkJoin, of } from 'rxjs';

import { TeamService, Team } from '../../../services/team.service';
import { TeamGroupService, TeamGroup } from '../../../services/team-group.service';
import {
  TeamJoinRequestService,
  TeamJoinRequest,
} from '../../../services/team-join-request.service';
import { AuthenticationService } from '../../../services/authentication.service';

@Component({
  selector: 'app-team-list',
  standalone: true,
  imports: [CommonModule, Navbar], // Cleaned imports
  templateUrl: './teams-list.html',
  styleUrls: ['./teams-list.css'],
})
export class TeamsListComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private teamService = inject(TeamService);
  private groupService = inject(TeamGroupService);
  private requestService = inject(TeamJoinRequestService);
  public authService = inject(AuthenticationService);
  public location = inject(Location);

  groupId!: number;
  group?: TeamGroup;
  teams: Team[] = [];
  loading = true;
  pendingRequestTeamIds: Set<number> = new Set();

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.groupId = +params['groupId'];
      if (this.groupId) this.loadData();
    });
  }

  loadData(): void {
    this.loading = true;
    const studentId = this.authService.user()?.student?.id;

    this.groupService.getGroup(this.groupId).subscribe({
      next: (group) => {
        this.group = group;
        forkJoin({
          teams: this.teamService.getTeams({ groupId: this.groupId }),
          myRequests: studentId
            ? this.requestService.getRequests({ studentId, status: 'PENDING' })
            : of([]),
        }).subscribe({
          next: (res) => {
            this.teams = res.teams;
            this.pendingRequestTeamIds = new Set(res.myRequests.map((r) => r.team.id));
            this.loading = false;
          },
          error: () => (this.loading = false),
        });
      },
      error: () => (this.loading = false),
    });
  }

  handleRequestJoin(team: Team) {
    const student = this.authService.user()?.student;
    if (!student) {
      alert('User session expired. Please log in again.');
      return;
    }

    // Ensure these are passed as pure numbers to match 'private Long' in Java
    const requestPayload: Omit<TeamJoinRequest, 'id' | 'status'> = {
      team: { id: team.id } as any,
      student: { id: student.id } as any,
    };

    console.log('Final Verification of Payload:', JSON.stringify(requestPayload));

    this.requestService.createRequest(requestPayload).subscribe({
      next: () => {
        alert('Success! Request sent to the leader.');
        this.loadData();
      },
      error: (err: any) => {
        console.error('Backend Error Body:', err.error);
        // If still 400, your backend might not allow 'status' in the POST body
        const errorMsg = err.error?.message || 'Server rejected the request format.';
        alert(`Join Request Failed: ${errorMsg}`);
      },
    });
  }

  getInitials(name: string): string {
    return name
      ? name
          .split(' ')
          .map((n) => n[0])
          .join('')
          .toUpperCase()
          .substring(0, 2)
      : '';
  }

  handleCreateTeam() {
    const student = this.authService.user()?.student;
    if (!student || !this.group) return;
    this.teamService
      .createTeam({
        group: { id: this.group.id } as any,
        leader: { id: student.id } as any,
      })
      .subscribe({
        next: (t) => this.router.navigate(['/student/team-details', t.id]),
        error: () => alert('Failed to create team.'),
      });
  }
}
