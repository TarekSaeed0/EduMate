import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Navbar } from '../../navbar/navbar';
import { Router } from '@angular/router'; // 1. Import Router

@Component({
  selector: 'app-team-creator',
  standalone: true,
  imports: [CommonModule, Navbar],
  templateUrl: './team-creator.html',
  styleUrls: ['./team-creator.css']
})
export class TeamCreator {

  // 2. Inject Router
  private router = inject(Router);

  showModal: boolean = false;

  closeModal() {
    this.showModal = false;
  }
  openModal() {
    this.showModal = true;
  }

  // Dummy data structure mirroring the SVG cards
  courses = [
    {
      code: 'CS304',
      name: 'Human Computer Interaction',
      status: 'No Team',
      deadline: 'Nov 20th',
      team: null,
      action: 'Find / Create Team',
      color: 'blue'
    },
    {
      code: 'CS201',
      name: 'Database Systems',
      status: 'Joined',
      deadline: 'Team: "SQL Masters"',
      team: 'SQL Masters',
      action: 'View Team',
      color: 'green'
    },
    {
      code: 'MATH302',
      name: 'Advanced Calculus',
      status: 'Closed',
      deadline: 'Deadline Passed',
      team: null,
      action: 'Unavailable',
      color: 'red'
    },
    {
      code: 'PHY101',
      name: 'General Physics',
      status: 'No Team',
      deadline: 'Dec 1st',
      team: null,
      action: 'Find / Create Team',
      color: 'orange'
    }
  ];

  handleCourseAction(courseCode: string, action: string) {
    if (action === 'Find / Create Team') {
      // 3. NAVIGATE to the new Team List page
      this.router.navigate(['/student/teams-list']);
    } else {
      // Placeholder for other actions (View Team, Unavailable)
      alert(`Action "${action}" triggered for course ${courseCode}`);
    }
  }
}
