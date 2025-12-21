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
import { AnnouncementService } from '../../services/announcement.service';
import { TeamGroupService } from '../../services/team-group.service';

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
  private announcementService = inject(AnnouncementService);
  private http = inject(HttpClient);
  private teamGroupService = inject(TeamGroupService);

  activeTab: 'courses' | 'tasks' | 'enrollment' | 'announcements' | 'teamGroups' = 'courses';

  coursesList: Course[] = [];
  semestersList: Semester[] = [];
  offeringsList: CourseOffering[] = [];
  studentsList: Student[] = [];
  enrolledStudentIds: number[] = [];
  selectedOfferingId: number | null = null;

  newCourse: Omit<Course, 'id'> = { code: '', name: '', credits: 3 };
  newOffering: any = { semesterId: null, course: { id: null } };
  newTask: any = { title: '', requirements: '', submissionUrl: '', dueDate: null, notes: '', offering: { id: null } };

  newAnnouncement: any = {
    title: '',
    content: '',
    scopeType: 'GLOBAL',
    scopeId: 0
  };

  newTeamGroup: any = {
    name: '',
    minimumMemberCount: 2,
    maximumMemberCount: 5,
    offering: { id: null } // Links to a specific course offering
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

  saveAnnouncement() {
    this.announcementService.createAnnouncement(this.newAnnouncement).subscribe({
      next: () => {
        alert('Announcement Published!');
        this.newAnnouncement = { title: '', content: '', scopeType: 'GLOBAL', scopeId: 0 };
      },
      error: (err: any) => { // Fixed TS7006 by adding ': any'
        console.error('Error publishing announcement:', err);
        alert('Failed to publish announcement.');
      }
    });
  }

  // --- Other existing methods ---
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

  saveTask() {
    this.taskService.createTask(this.newTask).subscribe({
      next: () => {
        alert('Task Published successfully!');
        this.newTask = { title: '', requirements: '', submissionUrl: '', dueDate: null, notes: '', offering: { id: null } };
      },
      error: (err: any) => console.error('Error creating task:', err)
    });
  }

  enrollStudent(studentId: number, offeringId: number) {
    const enrollmentData = { studentId: studentId, offering: { id: offeringId }, status: 'REGISTERED' };
    this.http.post('http://localhost:8080/api/registrations', enrollmentData, { withCredentials: true }).subscribe({
      next: () => {
        alert('Student Enrolled!');
        if (!this.enrolledStudentIds.includes(studentId)) this.enrolledStudentIds.push(studentId);
      },
      error: (err: any) => alert('Could not enroll student.')
    });
  }

  checkEnrollmentStatus() {
    if (!this.selectedOfferingId) { this.enrolledStudentIds = []; return; }
    this.http.get<number[]>(`http://localhost:8080/api/registrations/offering/${this.selectedOfferingId}`, { withCredentials: true })
      .subscribe({ next: (ids) => this.enrolledStudentIds = ids });
  }
  saveTeamGroup() {
    if (!this.newTeamGroup.offering.id) {
      alert("Please select a course offering first.");
      return;
    }

    this.teamGroupService.createGroup(this.newTeamGroup).subscribe({
      next: () => {
        alert('Course Team Group Created Successfully!');
        // Reset form
        this.newTeamGroup = { name: '', minimumMemberCount: 2, maximumMemberCount: 5, offering: { id: null } };
      },
      error: (err: any) => {
        console.error("Error creating group:", err);
        alert("Failed to create team group. Check if the course is already registered.");
      }
    });
  }

}
