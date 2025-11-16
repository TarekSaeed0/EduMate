import { inject, Injectable } from '@angular/core';
import { Gender } from './authentication.service';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';

export interface Student {
  id: number;
  name: string;
  gender: Gender;
  email: string;
  userId: number;
}

export interface Task {
  id: number;
  offeringId: number;
  title: string;
  requirements: string | null;
  submissionUrl: string | null;
  dueDate: Date | null;
  notes: string | null;
}

export interface StudentTask {
  id: number;
  studentId: number;
  task: Task;
  submittedAt: Date | null;
}

export interface StudentFaq {
  id: number;
  question: string;
  answer: string;
}

@Injectable({
  providedIn: 'root',
})
export class StudentService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/students';

  getStudent(id: number): Observable<Student> {
    return this.http.get<Student>(`${this.baseUrl}/${id}`, { withCredentials: true });
  }

  getStudentTasks(studentId: number): Observable<StudentTask[]> {
    return this.http
      .get<StudentTask[]>(`${this.baseUrl}/${studentId}/tasks`, {
        withCredentials: true,
      })
      .pipe(
        map((studentTasks) =>
          studentTasks.map((studentTask) => ({
            ...studentTask,
            task: {
              ...studentTask.task,
              dueDate: studentTask.task.dueDate ? new Date(studentTask.task.dueDate) : null,
            },
            submittedAt: studentTask.submittedAt ? new Date(studentTask.submittedAt) : null,
          })),
        ),
      );
  }

  getCurrentStudent(): Observable<Student> {
    return this.http.get<Student>(`${this.baseUrl}/me`, { withCredentials: true });
  }

  getCurrentStudentTasks(): Observable<StudentTask[]> {
    return this.http.get<StudentTask[]>(`${this.baseUrl}/me/tasks`, { withCredentials: true }).pipe(
      map((studentTasks) =>
        studentTasks.map((studentTask) => ({
          ...studentTask,
          task: {
            ...studentTask.task,
            dueDate: studentTask.task.dueDate ? new Date(studentTask.task.dueDate) : null,
          },
          submittedAt: studentTask.submittedAt ? new Date(studentTask.submittedAt) : null,
        })),
      ),
    );
  }
}
