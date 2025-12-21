import { Component, OnInit, inject } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Navbar } from '../../navbar/navbar';

import { TeamService, Team } from '../../../services/team.service';
import { TeamJoinRequestService, TeamJoinRequest } from '../../../services/team-join-request.service';
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
  private authService = inject(AuthenticationService);
  public location = inject(Location);

  teamId!: number;
  team?: Team;
  requests: TeamJoinRequest[] = [];
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
        if (this.isLeader) this.loadRequests();
        this.loading = false;
      },
      error: () => this.loading = false
    });
  }

  loadRequests(): void {
    this.requestService.getRequests({ teamId: this.teamId, status: 'PENDING' })
      .subscribe({
        next: (data) => this.requests = data,
        error: (err: any) => console.error("Error loading requests:", err)
      });
  }

  // Changed parameter to number | undefined to match HTML template
  acceptJoinRequest(requestId: number | undefined): void {
    if (!requestId) return;
    this.requestService.acceptRequest(requestId).subscribe({
      next: () => {
        alert('Student accepted!');
        this.loadTeamData();
      },
      error: (err: any) => alert('Accept failed: ' + (err.error?.message || 'Unknown error'))
    });
  }

  rejectJoinRequest(requestId: number | undefined): void {
    if (!requestId || !confirm('Reject this request?')) return;
    this.requestService.rejectRequest(requestId).subscribe({
      next: () => this.loadRequests(),
      error: (err: any) => console.error("Reject failed:", err)
    });
  }
}
