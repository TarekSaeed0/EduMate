import { Component, OnInit, inject } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Navbar } from '../../navbar/navbar';

import { TeamService, Team } from '../../../services/team.service';
import { TeamGroupService, TeamGroup } from '../../../services/team-group.service';
import { TeamJoinRequestService } from '../../../services/team-join-request.service';
import { AuthenticationService } from '../../../services/authentication.service';

@Component({
  selector: 'app-team-list',
  standalone: true,
  imports: [CommonModule, Navbar, RouterLink],
  templateUrl: './teams-list.html',
  styleUrls: ['./teams-list.css']
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

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.groupId = +params['groupId'];
      if (this.groupId) {
        this.loadData();
      }
    });
  }

  loadData(): void {
    this.loading = true;

    this.groupService.getGroup(this.groupId).subscribe({
      next: (group) => {
        this.group = group;
        this.loadTeams();
      },
      error: (err) => {
        console.error("Error loading group metadata", err);
        this.loading = false;
      }
    });
  }

  loadTeams(): void {
    this.teamService.getTeams({ groupId: this.groupId }).subscribe({
      next: (data) => {
        this.teams = data;
        this.loading = false;
      },
      error: (err: any) => {
        console.error("Error loading teams", err);
        this.loading = false;
      }
    });
  }

  getInitials(name: string): string {
    if (!name) return '';
    return name.split(' ').map(n => n[0]).join('').toUpperCase().substring(0, 2);
  }

  getMemberNames(team: Team): string {
    const names = [team.leader.name, ...team.members.map(m => m.name)];
    return names.join(', ');
  }

  handleJoin(team: Team) {
    const studentData = this.authService.user()?.student;
    if (!studentData) return;

    const requestPayload = {
      student: { id: studentData.id },
      team: { id: team.id },


      studentId: studentData.id,
      teamId: team.id

    };

    console.log('Sending Hybrid Payload:', requestPayload);

    this.requestService.createRequest(requestPayload as any).subscribe({
      next: () => {
        alert('Success! Request sent to team leader.');
        // Optional: Refresh the list to update UI
        this.loadTeams();
      },
      error: (err: any) => {
        console.error("Hybrid Join Failed:", err);
        const msg = err.error?.message || "Unknown error";
        alert(`Request failed: ${msg}`);
      }
    });
  }

  handleCreateTeam() {
    const student = this.authService.user()?.student;
    if (!student || !this.group) return;

    if (confirm(`Start a new team for ${this.group.name}?`)) {
      // FIXED: Changed members: [] to members: null to satisfy backend validation
      const newTeamPayload: any = {
        group: { id: this.group.id },
        leader: { id: student.id },
        members: null
      };

      this.teamService.createTeam(newTeamPayload).subscribe({
        next: (createdTeam) => {
          alert('Team created successfully!');
          this.router.navigate(['/student/team-details', createdTeam.id]);
        },
        error: (err: any) => {
          // This will now show the specific reason if it fails again
          console.error("Server rejected the request:", err.error?.message || err.statusText);
          alert('Failed to create team. Ensure you are not already a leader in this group.');
        }
      });
    }
  }
}
