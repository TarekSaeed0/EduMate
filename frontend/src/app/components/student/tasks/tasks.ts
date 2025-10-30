import { Component, inject, signal, ChangeDetectionStrategy } from '@angular/core';
import { StudentService, StudentTask } from '../../../services/student.service';
import { Navbar } from '../../navbar/navbar'

@Component({
  selector: 'app-student-tasks',
  standalone: true,
  imports: [Navbar],
  templateUrl: './tasks.html',
  styleUrls: ['./tasks.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,

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
    {id: 229, 
     studentId: 23010228,
     task: {id: 123, offeringId:567, title: "num3", requirements: "do gauss elimination", submissionUrl: "blah blah", dueDate: null, notes: null},
     submittedAt: null},
    {id: 230, 
     studentId: 23010228,
     task: {id: 123, offeringId:567, title: "discrete", requirements: "turn in the sheet", submissionUrl: "who cares", dueDate: null, notes: null},
     submittedAt: null},
    {id: 231, 
     studentId: 23010228,
     task: {id: 123, offeringId:567, title: "prog2", requirements: "do project", submissionUrl: "ahhhhhh!!!", dueDate: null, notes: null},
     submittedAt: null},
  ];

  selectedTask = signal<StudentTask | null>(null);

  viewTask(stdtsk: StudentTask) {
    console.log("Clicked:", stdtsk.task.title);
    this.selectedTask.set(stdtsk);
    console.log("Signal now:", this.selectedTask())
  }

  fetchStudentTasks() {

    this.studentTasks.set(this.stdTasks)

    /*
    this.studentService.getCurrentStudentTasks().subscribe({
      next: (tasks) => this.studentTasks.set(tasks),
      error: () => this.studentTasks.set([]),
    });*/
  }
}
