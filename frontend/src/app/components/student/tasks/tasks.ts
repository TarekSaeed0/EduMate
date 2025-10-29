import { Component, inject, signal } from '@angular/core';
import { StudentService, StudentTask } from '../../../services/student.service';

@Component({
  selector: 'app-student-tasks',
  imports: [],
  templateUrl: './tasks.html',
  styleUrl: './tasks.css',
})
export class StudentTasks {
  studentService = inject(StudentService);

  studentTasks = signal<StudentTask[]>([]);

  constructor() {
    this.fetchStudentTasks();
  }

  fetchStudentTasks() {
    this.studentService.getCurrentStudentTasks().subscribe({
      next: (tasks) => this.studentTasks.set(tasks),
      error: () => this.studentTasks.set([]),
    });
  }
}
