import { Component, computed, inject, signal } from '@angular/core';
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

  semester = computed(() => {
    const semester = this.authenticationService.user()?.student?.university.currentSemester;
    if (!semester) {
      return null;
    }

    const term = semester.term.charAt(0).toUpperCase() + semester.term.slice(1).toLowerCase();

    const year = semester.year;

    return `${term} ${year}-${year + 1}`;
  });

  courseColors = ['#0ea5e9', '#00B894', '#FFA726', '#9C27B0', '#E91E63'];

  getSessionColor(session: CourseSession) {
    return this.courseColors[session.offering.course.id % this.courseColors.length];
  }

  getSessions(i: number, j: number) {
    return this.timetable()?.sessions[i]?.[j] || [];
  }
}
