import { Component, inject, signal, ChangeDetectionStrategy } from '@angular/core';
import { StudentService, StudentTask } from '../../../services/student.service';
import { Navbar } from '../../navbar/navbar';

@Component({
  selector: 'app-student-tasks',
  standalone: true,
  imports: [Navbar],
  templateUrl: './tasks.html',
  styleUrls: ['./tasks.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class StudentTasks {
  private studentService = inject(StudentService);

  studentTasks = signal<StudentTask[]>([]);
  selectedTask = signal<StudentTask | null>(null);

  constructor() {
    this.fetchStudentTasks();
  }

  viewTask(task: StudentTask) {
    this.selectedTask.set(task);
  }

  fetchStudentTasks() {
    this.studentService.getCurrentStudentTasks().subscribe({
      next: (tasks: StudentTask[]) => this.studentTasks.set(tasks),
      error: () => this.studentTasks.set([]),
    });
  }
}
