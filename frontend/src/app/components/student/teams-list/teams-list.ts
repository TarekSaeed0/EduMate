// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-teams-list',
//   imports: [],
//   templateUrl: './teams-list.html',
//   styleUrl: './teams-list.css'
// })
// export class TeamsList {

// }

import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router'; // Needed for the "Back" button
import { Navbar } from '../../navbar/navbar';

@Component({
  selector: 'app-team-list',
  standalone: true,
  imports: [CommonModule, Navbar, RouterLink],
  templateUrl: './teams-list.html',
  styleUrls: ['./teams-list.css']
})
export class TeamsListComponent {

  // Data derived from the SVG
  courseInfo = {
    code: 'CS304',
    name: 'Human Computer Interaction',
    maxMembers: 5,
    deadline: 'Nov 20th, 2024',
    totalTeams: 12
  };

  teams = [
    {
      name: 'Frontend Wizards',
      spotsLabel: '2 Spots Left',
      status: 'Open', // Open, Full
      colorTheme: 'green', // For the colored strip and text
      members: [
        { initials: 'JD', color: 'green-light' },
        { initials: 'AS', color: 'blue-light' },
        { initials: 'MK', color: 'orange-light' }
      ],
      memberNames: 'John, Sarah, Mike'
    },
    {
      name: 'Design Squad',
      spotsLabel: '1 Spot Left',
      status: 'Open',
      colorTheme: 'orange',
      members: [
        { initials: 'AL', color: 'purple-light' },
        { initials: 'BT', color: 'green-light' },
        { initials: 'CR', color: 'pink-light' },
        { initials: 'DL', color: 'blue-light' }
      ],
      memberNames: 'Alice, Bob, Carol, Dave'
    },
    {
      name: 'Code Ninjas',
      spotsLabel: 'Full Team',
      status: 'Full',
      colorTheme: 'red',
      members: [
        { initials: '', color: 'gray' },
        { initials: '', color: 'gray' },
        { initials: '', color: 'gray' },
        { initials: '', color: 'gray' },
        { initials: '', color: 'gray' }
      ],
      memberNames: 'Tom, Jerry, Spike, Tyke, Butch'
    }
  ];

  handleJoin(teamName: string) {
    alert(`Request to join ${teamName} sent!`);
  }
}
