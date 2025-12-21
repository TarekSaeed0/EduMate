import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Navbar } from '../../navbar/navbar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-timetable',
  standalone: true,
  imports: [CommonModule, Navbar],
  templateUrl: './timetable.html',
  styleUrl: './timetable.css'
})
export class TimetableComponent {
  private router = inject(Router);

  // Your new days (6 total)
  days = ['Saturday','Sunday','Monday', 'Tuesday', 'Wednesday', 'Thursday'];

  // Your new time slots
  hours = ['08:30', '10:20', '12:10', '01:50', '03:40', '05:30'];

  scheduleMatrix: any = {
    'Monday': {
      '08:30': [
        { code: 'CS101', room: 'L1', color: 'blue' },
        { code: 'MATH2', room: 'R5', color: 'teal' }
      ]
    }
  };

  getSessions(day: string, hour: string) {
    return this.scheduleMatrix[day]?.[hour] || [];
  }

  navigateTo(path: string) {
    this.router.navigate([path]);
  }
}
