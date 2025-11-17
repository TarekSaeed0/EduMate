import { Component, signal, ChangeDetectionStrategy, HostListener } from '@angular/core';
import { StudentTask, StudentTaskStatus } from '../../../services/student.service';
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
  selectedStatus = signal<StudentTaskStatus | null>(null);

  // Store only one expanded task ID at a time
  expandedTaskId = signal<number | null>(null);

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
  {
    studentId: 1,
    task: {
      id: 102,
      offeringId: 204,
      title: 'Physics Lab Report – Pendulum Experiment',
      requirements: 'Document measurements and analyze results.',
      submissionUrl: 'https://submit.physics/lab3',
      dueDate: new Date('2025-11-28T17:00'),
      notes: 'Include diagrams and error analysis.'
    },
    submittedAt: null
  },
  {
    studentId: 1,
    task: {
      id: 103,
      offeringId: 205,
      title: 'Computer Networks Quiz Preparation',
      requirements: 'Study chapters 4–6 and solve sample questions.',
      submissionUrl: null,
      dueDate: new Date('2025-11-27T12:00'),
      notes: 'Focus on TCP/IP layers.'
    },
    submittedAt: null
  },
  {
    studentId: 1,
    task: {
      id: 104,
      offeringId: 210,
      title: 'Linear Algebra Quiz',
      requirements: 'Study chapters 5–7 and solve all exercises.',
      submissionUrl: null,
      dueDate: new Date('2025-11-30T09:00'),
      notes: 'Focus on eigenvalues and matrices.'
    },
    submittedAt: null
  },
  {
    studentId: 1,
    task: {
      id: 105,
      offeringId: 211,
      title: 'Software Engineering Project Proposal',
      requirements: 'Submit a project proposal with milestones and team members.',
      submissionUrl: 'https://submit.se/project1',
      dueDate: new Date('2025-12-05T23:59'),
      notes: 'Proposal must be approved before starting development.'
    },
    submittedAt: null
  },
  {
    studentId: 1,
    task: {
      id: 106,
      offeringId: 212,
      title: 'Economics Assignment – Market Structures',
      requirements: 'Answer case studies 1–3 with proper analysis.',
      submissionUrl: null,
      dueDate: new Date('2025-11-29T23:59'),
      notes: 'Include diagrams where relevant.'
    },
    submittedAt: null
  },
  {
    studentId: 1,
    task: {
      id: 107,
      offeringId: 213,
      title: 'Database Homework – SQL Queries',
      requirements: 'Write SQL queries for exercises 1–10.',
      submissionUrl: 'https://submit.db/homework',
      dueDate: new Date('2025-12-03T23:59'),
      notes: 'Test queries on sample database.'
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
  {
    studentId: 1,
    task: {
      id: 202,
      offeringId: 206,
      title: 'Chemistry Lab – Acid-Base Titration',
      requirements: 'Record all experimental data and write conclusions.',
      submissionUrl: null,
      dueDate: new Date('2025-10-20T16:00'),
      notes: 'Check units carefully.'
    },
    submittedAt: null
  },
  {
    studentId: 1,
    task: {
      id: 203,
      offeringId: 207,
      title: 'History Essay – Industrial Revolution',
      requirements: 'Write a 1500-word essay with references.',
      submissionUrl: null,
      dueDate: new Date('2025-10-10T23:59'),
      notes: 'Include at least 5 credible sources.'
    },
    submittedAt: null
  },
  {
    studentId: 1,
    task: {
      id: 204,
      offeringId: 214,
      title: 'Statistics Assignment – Probability Distributions',
      requirements: 'Solve exercises 1–15 in the textbook.',
      submissionUrl: null,
      dueDate: new Date('2025-10-18T23:59'),
      notes: 'Show all calculations clearly.'
    },
    submittedAt: null
  },
  {
    studentId: 1,
    task: {
      id: 205,
      offeringId: 215,
      title: 'Electrical Circuits Lab',
      requirements: 'Build and test the series and parallel circuits.',
      submissionUrl: null,
      dueDate: new Date('2025-10-12T17:00'),
      notes: 'Include oscilloscope screenshots.'
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
  },
  {
    studentId: 1,
    task: {
      id: 302,
      offeringId: 208,
      title: 'English Literature Assignment – Shakespeare Analysis',
      requirements: 'Analyze one play and summarize key themes.',
      submissionUrl: null,
      dueDate: new Date('2025-10-05T23:59'),
      notes: 'Use quotes for support.'
    },
    submittedAt: new Date('2025-10-04T15:30')
  },
  {
    studentId: 1,
    task: {
      id: 303,
      offeringId: 209,
      title: 'Programming Assignment – Recursion',
      requirements: 'Implement recursive functions for exercises 1–6.',
      submissionUrl: 'https://submit.cs/recursion',
      dueDate: new Date('2025-10-18T23:59'),
      notes: 'Test edge cases carefully.'
    },
    submittedAt: new Date('2025-10-18T10:20')
  },
  {
    studentId: 1,
    task: {
      id: 304,
      offeringId: 216,
      title: 'Biology Lab Report – Photosynthesis',
      requirements: 'Record experiment results and write discussion.',
      submissionUrl: null,
      dueDate: new Date('2025-10-08T17:00'),
      notes: 'Include data tables.'
    },
    submittedAt: new Date('2025-10-08T14:50')
  },
  {
    studentId: 1,
    task: {
      id: 305,
      offeringId: 217,
      title: 'Philosophy Essay – Ethics and Morality',
      requirements: 'Write a 1200-word essay analyzing moral theories.',
      submissionUrl: null,
      dueDate: new Date('2025-10-07T23:59'),
      notes: 'Use examples from case studies.'
    },
    submittedAt: new Date('2025-10-06T20:15')
  },

  // UPCOMING
  {
    studentId: 1,
    task: {
      id: 108,
      offeringId: 218,
      title: 'Marketing Assignment – SWOT Analysis',
      requirements: 'Analyze the assigned company and submit SWOT report.',
      submissionUrl: 'https://submit.marketing/assignment1',
      dueDate: new Date('2025-12-07T23:59'),
      notes: 'Provide clear recommendations.'
    },
    submittedAt: null
  },
  {
    studentId: 1,
    task: {
      id: 109,
      offeringId: 219,
      title: 'Mechanical Engineering Design Problem',
      requirements: 'Complete CAD design and submit diagrams.',
      submissionUrl: 'https://submit.me/design1',
      dueDate: new Date('2025-12-10T23:59'),
      notes: 'Ensure dimensions are accurate.'
    },
    submittedAt: null
  },
  {
    studentId: 1,
    task: {
      id: 110,
      offeringId: 220,
      title: 'Philosophy Reflection – Critical Thinking',
      requirements: 'Submit a 500-word reflection on assigned readings.',
      submissionUrl: null,
      dueDate: new Date('2025-12-02T23:59'),
      notes: 'Focus on logic and argument evaluation.'
    },
    submittedAt: null
  }
];

  constructor() {
    this.filterTasks();
  }

  toggleTask(studentTask: StudentTask, event: Event) {
    event.stopPropagation();
    // Toggle expansion: collapse if already expanded
    this.expandedTaskId.set(
      this.expandedTaskId() === studentTask.task.id ? null : studentTask.task.id
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

  @HostListener('document:click', ['$event'])
  collapseTasksOnOutsideClick(event: Event) {
    const target = event.target as HTMLElement;
    if (!target.closest('.task-item')) {
      this.expandedTaskId.set(null);
    }
  }
}
