import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Navbar } from '../../navbar/navbar';
import { StudentService, Timetable } from '../../../services/student.service';
import { AuthenticationService } from '../../../services/authentication.service';
import { CourseSession } from '../../../services/course-session.service';

@Component({
  selector: 'app-timetable',
  standalone: true,
  imports: [CommonModule, Navbar],
  templateUrl: './timetable.html',
  styleUrl: './timetable.css',
})
export class TimetableComponent {
  private authenticationService = inject(AuthenticationService);
  private studentService = inject(StudentService);

  timetable = signal<Timetable | null>(null);

  ngOnInit() {
    this.studentService
      .getStudentTimetable(this.authenticationService.user()!.student!.id)
      .subscribe((timetable) => this.timetable.set(timetable));
  }

  courseColors = [
    'red',
    'blue',
    'green',
    'orange',
    'purple',
    'teal',
    'pink',
    'brown',
    'indigo',
    'cyan',
    'lime',
    'amber',
    'deeporange',
    'lightblue',
    'yellow',
    'deepPurple',
    'lightGreen',
    'grey',
    'blueGrey',
    'magenta',
  ];

  getSessionColor(session: CourseSession) {
    return this.courseColors[session.offering.course.id % this.courseColors.length];
  }

  getSessions(i: number, j: number) {
    return this.timetable()?.sessions[i]?.[j] || [];
  }
}
