import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { forkJoin, of, delay, retry } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Navbar } from '../../navbar/navbar';
import { TeamGroupService, TeamGroup } from '../../../services/team-group.service';
import { TeamService, Team } from '../../../services/team.service';
import { AuthenticationService } from '../../../services/authentication.service';

@Component({
  selector: 'app-team-creator',
  standalone: true,
  imports: [CommonModule, Navbar],
  templateUrl: './team-creator.html',
  styleUrls: ['./team-creator.css']
})
export class TeamCreator implements OnInit {
  private router = inject(Router);
  private teamGroupService = inject(TeamGroupService);
  private teamService = inject(TeamService);
  private authService = inject(AuthenticationService);

  groups: TeamGroup[] = [];
  userTeams: Map<number, Team> = new Map();
  loading = true;

  ngOnInit(): void {
    this.checkAuthAndLoad();
  }

  private checkAuthAndLoad(attempts = 0): void {
    const user = this.authService.user();

    // If user isn't ready yet, try again 3 times with a small delay
    if (!user || !user.student) {
      if (attempts < 3) {
        setTimeout(() => this.checkAuthAndLoad(attempts + 1), 200);
      } else {
        console.warn("Auth state failed to initialize after 3 attempts.");
        this.loading = false;
      }
      return;
    }

    this.loadData(user.student.id);
  }

  loadData(studentId: number): void {
    this.teamGroupService.getGroups().subscribe({
      next: (groups) => {
        this.groups = groups;

        const teamChecks = groups.map(g =>
          this.teamService.getTeams({ groupId: g.id, memberId: studentId }).pipe(
            catchError(() => of([]))
          )
        );

        forkJoin(teamChecks).subscribe({
          next: (results) => {
            results.forEach((teams, index) => {
              if (teams && teams.length > 0) {
                this.userTeams.set(this.groups[index].id, teams[0]);
              }
            });
            this.loading = false;
          },
          error: () => this.loading = false
        });
      }
    });
  }

  getStatus(groupId: number): string {
    return this.userTeams.has(groupId) ? 'Joined' : 'No Team';
  }

  handleCourseAction(groupId: number) {
    if (this.userTeams.has(groupId)) {
      const teamId = this.userTeams.get(groupId)?.id;
      this.router.navigate(['/student/team-details', teamId]);
    } else {
      this.router.navigate(['/student/teams-list'], { queryParams: { groupId } });
    }
  }

  getCardColor(id: number): string {
    const colors = ['blue', 'green', 'orange', 'red'];
    return colors[id % colors.length];
  }
}
