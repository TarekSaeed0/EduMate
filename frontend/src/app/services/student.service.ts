import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { Task, TaskService } from './task.service';
import { CourseSession } from './course-session.service';
import { TimePeriod } from './time-period.service';
import { WeekDay } from './time-slot.service';
import { University, UniversityService } from './university.service';

export interface Student {
  id: number;
  university: University;
  name: string;
  userId: number;
}

export type StudentTaskStatus = 'UPCOMING' | 'OVERDUE' | 'COMPLETED';

export interface StudentTask {
  studentId: number;
  task: Task;
  submittedAt: Date | null;
  status: StudentTaskStatus;
}

export interface StudentTaskFilter {
  taskId?: number;
  semesterId?: number;
  courseId?: number;
  status?: StudentTaskStatus;
}

export interface Timetable {
  weekDays: WeekDay[];
  periods: TimePeriod[];
  sessions: CourseSession[][][];
}

@Injectable({
  providedIn: 'root',
})
export class StudentService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/students';

  static studentMapper = (student: Student): Student => ({
    ...student,
    university: UniversityService.universityMapper(student.university),
  });

  static studentTaskMapper = (studentTask: StudentTask): StudentTask => ({
    ...studentTask,
    task: TaskService.taskMapper(studentTask.task),
    submittedAt: studentTask.submittedAt ? new Date(studentTask.submittedAt) : null,
  });

  getStudents(): Observable<Student[]> {
    return this.http
      .get<Student[]>(this.baseUrl, { withCredentials: true })
      .pipe(map((students) => students.map(StudentService.studentMapper)));
  }

  getStudent(studentId: number): Observable<Student> {
    return this.http
      .get<Student>(`${this.baseUrl}/${studentId}`, { withCredentials: true })
      .pipe(map(StudentService.studentMapper));
  }

  getStudentTasks(studentId: number, filter?: StudentTaskFilter): Observable<StudentTask[]> {
    let params = new HttpParams();

    if (filter) {
      Object.entries(filter).forEach(([key, value]) => {
        if (Array.isArray(value)) {
          params = params.append(key, value.join(','));
        } else if (value !== undefined) {
          params = params.append(key, value);
        }
      });
    }

    return this.http
      .get<StudentTask[]>(`${this.baseUrl}/${studentId}/tasks`, {
        withCredentials: true,
        params,
      })
      .pipe(map((studentTasks) => studentTasks.map(StudentService.studentTaskMapper)));
  }

  getStudentTask(studentId: number, taskId: number): Observable<StudentTask> {
    return this.http
      .get<StudentTask>(`${this.baseUrl}/${studentId}/tasks/${taskId}`, {
        withCredentials: true,
      })
      .pipe(map(StudentService.studentTaskMapper));
  }

  submitStudentTask(studentId: number, taskId: number): Observable<StudentTask> {
    return this.http
      .post<StudentTask>(`${this.baseUrl}/${studentId}/tasks/${taskId}/submit`, null, {
        withCredentials: true,
      })
      .pipe(map(StudentService.studentTaskMapper));
  }

  unsubmitStudentTask(studentId: number, taskId: number): Observable<StudentTask> {
    return this.http
      .post<StudentTask>(`${this.baseUrl}/${studentId}/tasks/${taskId}/unsubmit`, null, {
        withCredentials: true,
      })
      .pipe(map(StudentService.studentTaskMapper));
  }

  getStudentTimetable(studentId: number): Observable<Timetable> {
    return this.http.get<Timetable>(`${this.baseUrl}/${studentId}/timetable`, {
      withCredentials: true,
    });
  }
}
