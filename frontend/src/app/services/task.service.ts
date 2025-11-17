import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';

export interface Task {
  id: number;
  offeringId: number;
  title: string;
  requirements: string | null;
  submissionUrl: string | null;
  dueDate: Date | null;
  notes: string | null;
}

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/tasks';

  static taskMapper = (task: Task): Task => ({
    ...task,
    dueDate: task.dueDate ? new Date(task.dueDate) : null,
  });

  getTasks(): Observable<Task[]> {
    return this.http
      .get<Task[]>(`${this.baseUrl}`, { withCredentials: true })
      .pipe(map((tasks) => tasks.map(TaskService.taskMapper)));
  }

  createTask(task: Omit<Task, 'id'>): Observable<Task> {
    return this.http
      .post<Task>(`${this.baseUrl}`, task, { withCredentials: true })
      .pipe(map(TaskService.taskMapper));
  }

  getTask(id: number): Observable<Task> {
    return this.http
      .get<Task>(`${this.baseUrl}/${id}`, { withCredentials: true })
      .pipe(map(TaskService.taskMapper));
  }

  updateTask(id: number, task: Omit<Task, 'id'>): Observable<Task> {
    return this.http
      .put<Task>(`${this.baseUrl}/${id}`, task, { withCredentials: true })
      .pipe(map(TaskService.taskMapper));
  }

  deleteTask(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`, { withCredentials: true });
  }
}
