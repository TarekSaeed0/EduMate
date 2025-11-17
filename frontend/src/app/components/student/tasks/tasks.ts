import { Component, inject, signal, ChangeDetectionStrategy } from '@angular/core'; 
import { StudentService, StudentTask, StudentTaskStatus } from '../../../services/student.service'; 
import { Task } from '../../../services/task.service'; 
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
  studentTasks = signal<StudentTask[]>([]);
  selectedTask = signal<StudentTask | null>(null);
  selectedStatus = signal<StudentTaskStatus | null>(null);

  tasks: StudentTask[] = [
    // UPCOMING
    {
      studentId: 1,
      task: {
        id: 101,
        offeringId: 201,
        title: 'AI Homework – Search Algorithms',
        requirements: 'Solve problems 1–5 from chapter 6.',
        submissionUrl: 'https://submit.ai/hw6',
        dueDate: new Date('2025-11-25T23:59'),
        notes: 'Focus on DFS vs BFS.'
      },
      submittedAt: null
    },
    // OVERDUE
    {
      studentId: 1,
      task: {
        id: 201,
        offeringId: 202,
        title: 'Calculus Assignment 4',
        requirements: 'Solve integrals from section 3.7.',
        submissionUrl: null,
        dueDate: new Date('2025-10-15T23:59'),
        notes: 'Late submissions not accepted.'
      },
      submittedAt: null
    },
    // COMPLETED
    {
      studentId: 1,
      task: {
        id: 301,
        offeringId: 203,
        title: 'Data Structures Assignment 2',
        requirements: 'Implement stack + queue using arrays.',
        submissionUrl: null,
        dueDate: new Date('2025-11-01T23:59'),
        notes: 'Efficiency matters.'
      },
      submittedAt: new Date('2025-10-30T17:45')
    }
  ];

  constructor() {
    this.filterTasks();
  }

  viewTask(task: StudentTask) {
    this.selectedTask.set(task);
  }

  toggleStatus(status: StudentTaskStatus | null) {
    this.selectedStatus.set(status);
    this.filterTasks();
  }

  filterTasks() {
    const now = new Date();

    const shownTasks = this.tasks.filter(task => {
      let status: StudentTaskStatus;

      if (task.submittedAt) {
        status = 'COMPLETED';
      } else if (task.task.dueDate && task.task.dueDate < now) {
        status = 'OVERDUE';
      } else {
        status = 'UPCOMING';
      }

      return this.selectedStatus() === null || status === this.selectedStatus();
    });

    this.studentTasks.set(shownTasks);
  }
}
