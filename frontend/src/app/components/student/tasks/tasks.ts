import { Component, signal, ChangeDetectionStrategy, HostListener, inject } from '@angular/core';
import { StudentService, StudentTask, StudentTaskStatus } from '../../../services/student.service';
import { Navbar } from '../../navbar/navbar';
import { AuthenticationService } from '../../../services/authentication.service';
import { TaskService, Task } from '../../../services/task.service';

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
  private taskService = inject(TaskService);

  studentTasks = signal<StudentTask[]>([]);
  selectedStatus = signal<StudentTaskStatus | null>(null);

  // Store only one expanded task ID at a time
  expandedTaskId = signal<number | null>(null);
  showCreateForm = signal<boolean>(false);
  isCreating = signal<boolean>(false);

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

  submitTask(studentTask: StudentTask, event: Event) {
    event.stopPropagation();
    this.studentService
      .submitStudentTask(this.authenticationService.user()!.student.id, studentTask.task.id)
      .subscribe({
        next: (updatedTask) => {
          // Update the task in the list
          this.studentTasks.update(tasks =>
            tasks.map(t => t.task.id === updatedTask.task.id ? updatedTask : t)
          );
        },
        error: (err) => console.error('Failed to submit task:', err)
      });
  }

  unsubmitTask(studentTask: StudentTask, event: Event) {
    event.stopPropagation();
    this.studentService
      .unsubmitStudentTask(this.authenticationService.user()!.student.id, studentTask.task.id)
      .subscribe({
        next: (updatedTask) => {
          // Update the task in the list
          this.studentTasks.update(tasks =>
            tasks.map(t => t.task.id === updatedTask.task.id ? updatedTask : t)
          );
        },
        error: (err) => console.error('Failed to unsubmit task:', err)
      });
  }

  getTaskStatus(studentTask: StudentTask): 'COMPLETED' | 'OVERDUE' | 'UPCOMING' {
    if (studentTask.submittedAt) return 'COMPLETED';
    if (!studentTask.task.dueDate) return 'UPCOMING';
    return new Date() > studentTask.task.dueDate ? 'OVERDUE' : 'UPCOMING';
  }

  isOverdue(studentTask: StudentTask): boolean {
    if (studentTask.submittedAt) return false;
    if (!studentTask.task.dueDate) return false;
    return new Date() > studentTask.task.dueDate;
  }
  openCreateForm() {
    this.showCreateForm.set(true);
  }

  closeCreateForm() {
    this.showCreateForm.set(false);
  }

  onSubmitCreateTask(event: Event) {
    event.preventDefault();
    const form = event.target as HTMLFormElement;
    const formData = new FormData(form);

    const dueDateStr = formData.get('dueDate') as string;
    const dueTimeStr = formData.get('dueTime') as string;

    let dueDate: Date | null = null;
    if (dueDateStr && dueTimeStr) {
      dueDate = new Date(`${dueDateStr}T${dueTimeStr}`);
    } else if (dueDateStr) {
      dueDate = new Date(dueDateStr);
    }

    const newTask: Omit<Task, 'id'> = {
      title: formData.get('title') as string,
      requirements: (formData.get('requirements') as string) || null,
      submissionUrl: (formData.get('submissionUrl') as string) || null,
      dueDate: dueDate,
      notes: (formData.get('notes') as string) || null,
      offering: { id: Number(formData.get('offeringId')) } as any,
    };

    this.isCreating.set(true);

    this.taskService.createTask(newTask).subscribe({
      next: (createdTask) => {
        console.log('Task created successfully:', createdTask);
        this.isCreating.set(false);
        this.closeCreateForm();
        form.reset();
        this.filterTasks();
      },
      error: (err) => {
        console.error('Failed to create task:', err);
        alert('Failed to create task. Please check the console for details.');
        this.isCreating.set(false);
      }
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
