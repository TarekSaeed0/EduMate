import { Component, inject, signal } from '@angular/core';
import { StudentService, StudentTask } from '../../../services/student.service';
import { Navbar } from '../../navbar/navbar'

@Component({
  selector: 'app-student-tasks',
  standalone: true,
  imports: [Navbar],
  templateUrl: './tasks.html',
  styleUrls: ['./tasks.css'],
})
export class StudentTasks {
  studentService = inject(StudentService);

  studentTasks = signal<StudentTask[]>([]);

  constructor() {
    this.fetchStudentTasks();
  }

  //remove this later
  stdTasks: StudentTask[] = [
    {id: 228, 
     studentId: 23010228,
     task: {id: 123, offeringId:567, title: "COLab6", requirements: "turnOn a led", submissionUrl: "blah blah", dueDate: null, notes: null},
     submittedAt: null},
    {id: 228, 
     studentId: 23010228,
     task: {id: 123, offeringId:567, title: "COLab6", requirements: "turnOn a led", submissionUrl: "blah blah", dueDate: null, notes: null},
     submittedAt: null},
    {id: 228, 
     studentId: 23010228,
     task: {id: 123, offeringId:567, title: "COLab6", requirements: "turnOn a led", submissionUrl: "blah blah", dueDate: null, notes: null},
     submittedAt: null},
    {id: 228, 
     studentId: 23010228,
     task: {id: 123, offeringId:567, title: "COLab6", requirements: "turnOn a led", submissionUrl: "blah blah", dueDate: null, notes: null},
     submittedAt: null},
  ];

  fetchStudentTasks() {

    this.studentTasks.set(this.stdTasks)

    /*
    this.studentService.getCurrentStudentTasks().subscribe({
      next: (tasks) => this.studentTasks.set(tasks),
      error: () => this.studentTasks.set([]),
    });*/
  }
}
