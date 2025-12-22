import { Component, OnInit, inject } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Navbar } from '../../navbar/navbar';

import { TeamService, Team } from '../../../services/team.service';
import { TeamJoinRequestService, TeamJoinRequest } from '../../../services/team-join-request.service';
import { TeamJoinInviteService, TeamJoinInvite } from '../../../services/team-join-invite.service';
import { AuthenticationService } from '../../../services/authentication.service';

@Component({
  selector: 'app-team-details',
  standalone: true,
  imports: [CommonModule, Navbar],
  templateUrl: './team-details.html',
  styleUrls: ['./team-details.css']
})
export class TeamDetailsComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private teamService = inject(TeamService);
  private requestService = inject(TeamJoinRequestService);
  private inviteService = inject(TeamJoinInviteService);
  private authService = inject(AuthenticationService);
  public location = inject(Location);

  teamId!: number;
  team?: Team;
  requests: TeamJoinRequest[] = [];
  invites: TeamJoinInvite[] = [];
  loading = true;

  get isLeader(): boolean {
    const studentId = this.authService.user()?.student?.id;
    return !!this.team && this.team.leader.id === studentId;
  }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.teamId = +idParam;
      this.loadTeamData();
    }
  }

  loadTeamData(): void {
    this.loading = true;
    this.teamService.getTeam(this.teamId).subscribe({
      next: (team) => {
        this.team = team;
        if (this.isLeader) this.loadManagementData();
        this.loading = false;
      },
      error: () => this.loading = false
    });
  }

  loadManagementData(): void {
    // Fetches student requests to join this team
    this.requestService.getRequests({ teamId: this.teamId, status: 'PENDING' })
      .subscribe(data => this.requests = data);

    // Fetches invites sent by this leader to students
    this.inviteService.getInvites({ teamId: this.teamId, status: 'PENDING' })
      .subscribe(data => this.invites = data);
  }

  acceptJoinRequest(requestId: number | undefined): void {
    if (!requestId) return;
    this.requestService.acceptRequest(requestId).subscribe({
      next: () => {
        alert('Student accepted! They are now in the team.');
        this.loadTeamData(); // Refreshes to show the student in the member list
      },
      error: (err) => alert('Accept failed: ' + (err.error?.message || 'Error'))
    });
  }

  rejectJoinRequest(requestId: number | undefined): void {
    if (!requestId || !confirm('Reject this request?')) return;
    this.requestService.rejectRequest(requestId).subscribe({
      next: () => this.loadManagementData(),
      error: (err) => console.error("Reject failed:", err)
    });
  }
}
