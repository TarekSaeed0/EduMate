import { Component, OnInit, inject } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Navbar } from '../../navbar/navbar';

// Service Imports
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

  // Logic: Compares the logged-in student's ID with the Team Leader's ID
  get isLeader(): boolean {
    const loggedInStudentId = this.authService.user()?.student?.id;
    return !!this.team && this.team.leader.id === loggedInStudentId;
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
        // If the current user is the leader, fetch the pending requests for this team
        if (this.isLeader) {
          this.loadRequests();
        }
        this.loading = false;
      },
      error: (err) => {
        console.error("Error loading team data:", err);
        this.loading = false;
      }
    });
  }

  loadRequests(): void {
    // Filter: find requests for this team where status is 'PENDING'
    this.requestService.getRequests({ teamId: this.teamId, status: 'PENDING' })
      .subscribe({
        next: (data) => this.requests = data,
        error: (err) => console.error("Error loading requests:", err)
      });
  }

  // --- LEADER ACTIONS ---

  acceptJoinRequest(requestId: number): void {
    this.requestService.acceptRequest(requestId).subscribe({
      next: () => {
        alert('Student accepted!');
        // Re-load both team and requests to update the UI
        this.loadTeamData();
      },
      error: (err: any) => {
        console.error("Accept request failed:", err);
        alert('Error: Team might be full or the student is already in another team.');
      }
    });
  }

  rejectJoinRequest(requestId: number): void {
    if (confirm('Reject this join request?')) {
      this.requestService.rejectRequest(requestId).subscribe({
        next: () => {
          // Only need to refresh requests if rejected
          this.loadRequests();
        },
        error: (err) => console.error("Reject request failed:", err)
      });
    }
  }
}
