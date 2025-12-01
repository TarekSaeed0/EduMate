import { Component, signal, ChangeDetectionStrategy, HostListener, inject } from '@angular/core';
import { StudentService, StudentTask, StudentTaskStatus } from '../../../services/student.service';
import { Navbar } from '../../navbar/navbar';
import { AuthenticationService } from '../../../services/authentication.service';

@Component({
  selector: 'app-student-tasks',
  standalone: true,
  imports: [Navbar],
  templateUrl: './tasks.html',
  styleUrls: ['./tasks.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class StudentTasks {
  private authenticationService = inject(AuthenticationService);
  private studentService = inject(StudentService);

  studentTasks = signal<StudentTask[]>([]);
  selectedStatus = signal<StudentTaskStatus | null>(null);

  // Store only one expanded task ID at a time
  expandedTaskId = signal<number | null>(null);

  constructor() {
    this.filterTasks();
  }

  toggleTask(studentTask: StudentTask, event: Event) {
    event.stopPropagation();
    // Toggle expansion: collapse if already expanded
    this.expandedTaskId.set(
      this.expandedTaskId() === studentTask.task.id ? null : studentTask.task.id,
    );
  }

  isExpanded(studentTask: StudentTask): boolean {
    return this.expandedTaskId() === studentTask.task.id;
  }

  toggleStatus(status: StudentTaskStatus | null) {
    this.selectedStatus.set(status);
    this.filterTasks();
  }

  filterTasks() {
    this.studentService
      .getStudentTasks(this.authenticationService.user()!.student.id, {
        status: this.selectedStatus() ?? undefined,
      })
      .subscribe({
        next: (studentTasks) => this.studentTasks.set(studentTasks),
        error: () => this.studentTasks.set([]),
      });
  }

  @HostListener('document:click', ['$event'])
  collapseTasksOnOutsideClick(event: Event) {
    const target = event.target as HTMLElement;
    if (!target.closest('.task-item')) {
      this.expandedTaskId.set(null);
    }
  }
}
