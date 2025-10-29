import { Component, computed, inject, signal } from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';
import { RouterLink } from '@angular/router';
import { Student, StudentService } from '../../services/student.service';

@Component({
  selector: 'app-home',
  imports: [RouterLink],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
  authenticationService = inject(AuthenticationService);
  private studentService = inject(StudentService);

  student = signal<Student | null>(null);
  greeting = computed(() =>
    this.student() ? `Hello, ${this.student()!.name.split(' ')[0]}!` : 'Hello!',
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

  signout() {
    this.authenticationService.signout().subscribe();
  }
}
