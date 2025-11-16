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

  // Dummy data for testing
  stdTasks: StudentTask[] = [
    {
      id: 228,
      studentId: 23010228,
      task: {
        id: 123,
        offeringId: 567,
        title: 'COLab6',
        requirements: 'turnOn a led',
        submissionUrl: 'blah blah',
        dueDate: null,
        notes: null,
      },
      submittedAt: null,
    },
    {
      id: 229,
      studentId: 23010228,
      task: {
        id: 123,
        offeringId: 567,
        title: 'num3',
        requirements: 'do gauss elimination',
        submissionUrl: 'blah blah',
        dueDate: null,
        notes: null,
      },
      submittedAt: null,
    },
    {
      id: 230,
      studentId: 23010228,
      task: {
        id: 123,
        offeringId: 567,
        title: 'discrete',
        requirements: 'turn in the sheet',
        submissionUrl: 'who cares',
        dueDate: null,
        notes: null,
      },
      submittedAt: null,
    },
    {
      id: 231,
      studentId: 23010228,
      task: {
        id: 123,
        offeringId: 567,
        title: 'prog2',
        requirements: 'do project',
        submissionUrl: 'ahhhhhh!!!',
        dueDate: null,
        notes: null,
      },
      submittedAt: null,
    },
    {
      id: 231,
      studentId: 23010228,
      task: {
        id: 123,
        offeringId: 567,
        title: 'prog2',
        requirements: 'do project',
        submissionUrl: 'ahhhhhh!!!',
        dueDate: null,
        notes: null,
      },
      submittedAt: null,
    },
    {
      id: 231,
      studentId: 23010228,
      task: {
        id: 123,
        offeringId: 567,
        title: 'prog2',
        requirements: 'do project',
        submissionUrl: 'ahhhhhh!!!',
        dueDate: null,
        notes: null,
      },
      submittedAt: null,
    },
    {
      id: 231,
      studentId: 23010228,
      task: {
        id: 123,
        offeringId: 567,
        title: 'prog2',
        requirements: 'do project',
        submissionUrl: 'ahhhhhh!!!',
        dueDate: null,
        notes: null,
      },
      submittedAt: null,
    },
    {
      id: 231,
      studentId: 23010228,
      task: {
        id: 123,
        offeringId: 567,
        title: 'prog2',
        requirements: 'do project',
        submissionUrl: 'ahhhhhh!!!',
        dueDate: null,
        notes: null,
      },
      submittedAt: null,
    },
  ];

  viewTask(task: StudentTask) {
    this.selectedTask.set(task);
  }

  fetchStudentTasks() {
    // Using dummy data
    this.studentTasks.set(this.stdTasks);

    // Or fetch from service safely:
    /*
    this.studentService.getCurrentStudentTasks().subscribe({
      next: (tasks: StudentTask[]) => this.studentTasks.set(tasks),
      error: () => this.studentTasks.set([]),
    });
    */
  }
}
