import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Navbar } from '../navbar/navbar';
import { CourseService, Course } from '../../services/course.service';
import { TaskService, Task } from '../../services/task.service';
import { CourseOfferingService, CourseOffering } from '../../services/course-offering.service';
import { SemesterService, Semester } from '../../services/semester.service';
import { StudentService, Student } from '../../services/student.service';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule, Navbar],
  templateUrl: './admin-dashboard.html',
  styleUrl: './admin-dashboard.css'
})
export class AdminDashboard implements OnInit {
  private courseService = inject(CourseService);
  private taskService = inject(TaskService);
  private offeringService = inject(CourseOfferingService);
  private semesterService = inject(SemesterService);
  private studentService = inject(StudentService);
  private http = inject(HttpClient);

  activeTab: 'courses' | 'tasks' | 'enrollment' = 'courses';

  coursesList: Course[] = [];
  semestersList: Semester[] = [];
  offeringsList: CourseOffering[] = [];
  studentsList: Student[] = [];
  enrolledStudentIds: number[] = [];

  selectedOfferingId: number | null = null;

  newCourse: Omit<Course, 'id'> = { code: '', name: '', credits: 3 };
  newOffering: any = { semesterId: null, course: { id: null } };

  newTask: any = {
    title: '',
    requirements: '',
    submissionUrl: '',
    dueDate: null,
    notes: '',
    offering: { id: null }
  };

  ngOnInit() {
    this.refreshAllData();
  }

  refreshAllData() {
    this.courseService.getCourses().subscribe(data => this.coursesList = data);
    this.semesterService.getSemesters().subscribe(data => this.semestersList = data);
    this.offeringService.getOfferings().subscribe(data => this.offeringsList = data);
    this.studentService.getStudents().subscribe(data => this.studentsList = data);
  }

  checkEnrollmentStatus() {
    if (!this.selectedOfferingId) {
      this.enrolledStudentIds = [];
      return;
    }
    this.http.get<number[]>(`http://localhost:8080/api/registrations/offering/${this.selectedOfferingId}`, {
      withCredentials: true
    }).subscribe({
      next: (ids) => this.enrolledStudentIds = ids,
      error: (err) => console.error("Error fetching enrollment status", err)
    });
  }

  saveCourse() {
    this.courseService.createCourse(this.newCourse).subscribe(() => {
      alert('Subject Saved!');
      this.refreshAllData();
    });
  }

  saveOffering() {
    this.offeringService.createOffering(this.newOffering).subscribe(() => {
      alert('Offering Activated!');
      this.refreshAllData();
    });
  }

  enrollStudent(studentId: number, offeringId: number) {
    // FIX: Structuring the payload to match CourseRegistrationDTO.java
    const enrollmentData = {
      studentId: studentId,
      offering: { id: offeringId }, // Matches the CourseOfferingDTO requirement
      status: 'REGISTERED'
    };

    this.http.post('http://localhost:8080/api/registrations', enrollmentData, {
      withCredentials: true
    }).subscribe({
      next: () => {
        alert('Student Enrolled and Tasks Assigned!');
        if (!this.enrolledStudentIds.includes(studentId)) {
          this.enrolledStudentIds.push(studentId);
        }
      },
      error: (err) => {
        console.error('Enrollment error:', err);
        alert('Could not enroll student. Ensure the backend Registration Service is updated.');
      }
    });
  }

  saveTask() {
    this.taskService.createTask(this.newTask).subscribe({
      next: () => {
        alert('Task Published successfully!');
        this.newTask = { title: '', requirements: '', submissionUrl: '', dueDate: null, notes: '', offering: { id: null } };
      },
      error: (err) => console.error('Error creating task:', err)
    });
  }
}
