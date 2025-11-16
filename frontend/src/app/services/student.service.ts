import { inject, Injectable } from '@angular/core';
import { Gender } from './authentication.service';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { Task, TaskService } from './task.service';
import { SemesterCourse } from './semester.service';

export interface Student {
  id: number;
  name: string;
  gender: Gender;
  email: string;
  userId: number;
}

export type StudentTaskStatus = 'UPCOMING' | 'OVERDUE' | 'COMPLETED';

export interface StudentTask {
  studentId: number;
  task: Task;
  submittedAt: Date | null;
}

export type StudentCourseStatus = 'REGISTERED' | 'PASSED' | 'FAILED' | 'DROPPED';

export interface StudentCourse {
  studentId: number;
  semesterCourse: SemesterCourse;
  status: StudentCourseStatus;
}

@Injectable({
  providedIn: 'root',
})
export class StudentService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/students';

  static studentTaskMapper = (studentTask: StudentTask): StudentTask => ({
    ...studentTask,
    task: TaskService.taskMapper(studentTask.task),
    submittedAt: studentTask.submittedAt ? new Date(studentTask.submittedAt) : null,
  });

  getStudent(id: number): Observable<Student> {
    return this.http.get<Student>(`${this.baseUrl}/${id}`, { withCredentials: true });
  }

  getStudentTasks(
    studentId: number,
    courseId?: number,
    status?: StudentTaskStatus,
  ): Observable<StudentTask[]> {
    return this.http
      .get<StudentTask[]>(`${this.baseUrl}/${studentId}/tasks`, {
        withCredentials: true,
        params: {
          ...(courseId && { courseId }),
          ...(status && { status }),
        },
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

  getStudentCourses(studentId: number): Observable<StudentCourse[]> {
    return this.http.get<StudentCourse[]>(`${this.baseUrl}/${studentId}/courses`, {
      withCredentials: true,
    });
  }

  createStudentCourse(
    studentId: number,
    studentCourse: Omit<StudentCourse, 'id'>,
  ): Observable<StudentCourse> {
    return this.http.post<StudentCourse>(`${this.baseUrl}/${studentId}/courses`, studentCourse, {
      withCredentials: true,
    });
  }

  getStudentCourse(studentId: number, semesterCourseId: number): Observable<StudentCourse> {
    return this.http.get<StudentCourse>(
      `${this.baseUrl}/${studentId}/courses/${semesterCourseId}`,
      { withCredentials: true },
    );
  }

  updateStudentCourse(
    studentId: number,
    semesterCourseId: number,
    studentCourse: Omit<StudentCourse, 'id'>,
  ): Observable<StudentCourse> {
    return this.http.put<StudentCourse>(
      `${this.baseUrl}/${studentId}/courses/${semesterCourseId}`,
      studentCourse,
      { withCredentials: true },
    );
  }

  deleteStudentCourse(studentId: number, semesterCourseId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${studentId}/courses/${semesterCourseId}`, {
      withCredentials: true,
    });
  }

  getCurrentStudent(): Observable<Student> {
    return this.http.get<Student>(`${this.baseUrl}/me`, { withCredentials: true });
  }

  getCurrentStudentTasks(courseId?: number, status?: StudentTaskStatus): Observable<StudentTask[]> {
    return this.http
      .get<StudentTask[]>(`${this.baseUrl}/me/tasks`, {
        withCredentials: true,
        params: {
          ...(courseId && { courseId }),
          ...(status && { status }),
        },
      })
      .pipe(map((studentTasks) => studentTasks.map(StudentService.studentTaskMapper)));
  }

  getCurrentStudentTask(taskId: number): Observable<StudentTask> {
    return this.http
      .get<StudentTask>(`${this.baseUrl}/me/tasks/${taskId}`, {
        withCredentials: true,
      })
      .pipe(map(StudentService.studentTaskMapper));
  }

  getCurrentStudentCourses(): Observable<StudentCourse[]> {
    return this.http.get<StudentCourse[]>(`${this.baseUrl}/me/courses`, {
      withCredentials: true,
    });
  }

  createCurrentStudentCourse(studentCourse: Omit<StudentCourse, 'id'>): Observable<StudentCourse> {
    return this.http.post<StudentCourse>(`${this.baseUrl}/me/courses`, studentCourse, {
      withCredentials: true,
    });
  }

  getCurrentStudentCourse(semesterCourseId: number): Observable<StudentCourse> {
    return this.http.get<StudentCourse>(`${this.baseUrl}/me/courses/${semesterCourseId}`, {
      withCredentials: true,
    });
  }

  updateCurrentStudentCourse(
    semesterCourseId: number,
    studentCourse: Omit<StudentCourse, 'id'>,
  ): Observable<StudentCourse> {
    return this.http.put<StudentCourse>(
      `${this.baseUrl}/me/courses/${semesterCourseId}`,
      studentCourse,
      { withCredentials: true },
    );
  }

  deleteCurrentStudentCourse(semesterCourseId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/me/courses/${semesterCourseId}`, {
      withCredentials: true,
    });
  }
}
