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
  submissiocnUrl: string | null;
  dueDate: Date | null;
  notes: string | null;
}

export interface StudentTask {
  studentId: number;
  task: Task;
  submittedAt: Date | null;
}

export type StudentTaskStatus = 'UPCOMING' | 'OVERDUE' | 'COMPLETED';

export interface Course {
  id: number;
  code: string;
  name: string;
  credits: number;
}

export interface SemesterCourse {
  id: number;
  semesterId: number;
  course: Course;
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

  private studentTaskMapper = (studentTask: StudentTask) => ({
    ...studentTask,
    task: {
      ...studentTask.task,
      dueDate: studentTask.task.dueDate ? new Date(studentTask.task.dueDate) : null,
    },
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
      .pipe(map((studentTasks) => studentTasks.map(this.studentTaskMapper)));
  }

  getStudentTask(studentId: number, taskId: number): Observable<StudentTask> {
    return this.http
      .get<StudentTask>(`${this.baseUrl}/${studentId}/tasks/${taskId}`, {
        withCredentials: true,
      })
      .pipe(map(this.studentTaskMapper));
  }

  getStudentCourses(studentId: number): Observable<StudentCourse[]> {
    return this.http.get<StudentCourse[]>(`${this.baseUrl}/${studentId}/courses`, {
      withCredentials: true,
    });
  }

  createStudentCourse(studentId: number, studentCourse: StudentCourse): Observable<StudentCourse> {
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
    studentCourse: StudentCourse,
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
      .pipe(map((studentTasks) => studentTasks.map(this.studentTaskMapper)));
  }

  getCurrentStudentTask(taskId: number): Observable<StudentTask> {
    return this.http
      .get<StudentTask>(`${this.baseUrl}/me/tasks/${taskId}`, {
        withCredentials: true,
      })
      .pipe(map(this.studentTaskMapper));
  }

  getCurrentStudentCourses(): Observable<StudentCourse[]> {
    return this.http.get<StudentCourse[]>(`${this.baseUrl}/me/courses`, {
      withCredentials: true,
    });
  }

  createCurrentStudentCourse(studentCourse: StudentCourse): Observable<StudentCourse> {
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
    studentCourse: StudentCourse,
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
