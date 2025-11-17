import { Component, computed, inject, signal } from '@angular/core';
import { Navbar } from '../navbar/navbar';
import { Student, StudentService } from '../../services/student.service';
import { AuthenticationService } from '../../services/authentication.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-main',
  imports: [Navbar],
  templateUrl: './main.html',
  styleUrls: ['./main.css']
})
export class Main {
  private studentService = inject(StudentService);

  student = signal<Student | null>(null);
  displayName = computed(() =>
    `${this.student()!.name.split(' ')[0]}`,
  );

  displayID = computed(() =>
    `${this.student()!.id}`,
  );

  constructor() {
    this.fetchStudent();
  }

  fetchStudent() {
    this.studentService.getCurrentStudent().subscribe({
      next: (student) => this.student.set(student),
      error: () => this.student.set(null),
    });
  }
}
