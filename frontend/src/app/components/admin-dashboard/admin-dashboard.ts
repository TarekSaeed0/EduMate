import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Navbar } from '../navbar/navbar';
import { CourseService, Course } from '../../services/course.service';
import { CourseMaterialService } from '../../services/course-material.service';
import { TaskService } from '../../services/task.service';
import { CourseOfferingService } from '../../services/course-offering.service';
import { SemesterService } from '../../services/semester.service';
import { StudentService } from '../../services/student.service';
import { AnnouncementService } from '../../services/announcement.service';
import { TeamGroupService } from '../../services/team-group.service';
import { CourseRegistrationService } from '../../services/course-registration.service';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule, Navbar],
  templateUrl: './admin-dashboard.html',
  styleUrl: './admin-dashboard.css',
})
export class AdminDashboard implements OnInit {
  private courseService = inject(CourseService);
  private materialService = inject(CourseMaterialService);
  private taskService = inject(TaskService);
  private offeringService = inject(CourseOfferingService);
  private registrationService = inject(CourseRegistrationService);
  private semesterService = inject(SemesterService);
  private studentService = inject(StudentService);
  private announcementService = inject(AnnouncementService);
  private teamGroupService = inject(TeamGroupService);

  activeTab: 'courses' | 'tasks' | 'enrollment' | 'announcements' | 'teamGroups' | 'materials' =
    'courses';

  coursesList: Course[] = [];
  materialsList: any[] = [];
  offeringsList: any[] = [];
  semestersList: any[] = [];
  studentsList: any[] = [];
  enrolledStudentIds: number[] = [];

  isEditingMaterial = false;
  selectedOfferingId: number | null = null;

  // Unified form models
  materialForm = {
    id: undefined as number | undefined,
    title: '',
    url: '',
    course: { id: null as number | null },
  };
  newCourse = { code: '', name: '', credits: 3 };
  newOffering = { semesterId: null, course: { id: null } };
  newTask = {
    title: '',
    requirements: '',
    submissionUrl: '',
    dueDate: null,
    notes: '',
    offering: { id: null },
  };
  newAnnouncement = { title: '', content: '', scopeType: 'GLOBAL', scopeId: 0 };
  newTeamGroup = { name: '', minimumMemberCount: 2, maximumMemberCount: 5, offering: { id: null } };

  ngOnInit() {
    this.refreshAllData();
  }

  refreshAllData() {
    this.courseService.getCourses().subscribe((data) => (this.coursesList = data));
    this.semesterService.getSemesters().subscribe((data) => (this.semestersList = data));
    this.offeringService.getOfferings().subscribe((data) => (this.offeringsList = data));
    this.studentService.getStudents().subscribe((data) => (this.studentsList = data));
    this.loadMaterials();
  }

  loadMaterials() {
    this.materialService.getMaterials().subscribe((data) => (this.materialsList = data));
  }

  saveMaterial() {
    if (!this.materialForm.course.id || !this.materialForm.title) return;

    if (this.isEditingMaterial && this.materialForm.id) {
      this.materialService
        .updateMaterial(this.materialForm.id, this.materialForm as any)
        .subscribe({
          next: () => {
            alert('Material Updated!');
            this.resetMaterialForm();
            this.loadMaterials();
          },
        });
    } else {
      this.materialService.createMaterial(this.materialForm as any).subscribe({
        next: () => {
          alert('Material Created!');
          this.resetMaterialForm();
          this.loadMaterials();
        },
      });
    }
  }

  editMaterial(item: any) {
    this.isEditingMaterial = true;
    this.materialForm = {
      id: item.id,
      title: item.title,
      url: item.url,
      course: { id: item.course.id },
    };
    this.activeTab = 'materials';
  }

  deleteMaterial(id: number) {
    if (confirm('Delete permanently?')) {
      this.materialService.deleteMaterial(id).subscribe(() => this.loadMaterials());
    }
  }

  resetMaterialForm() {
    this.isEditingMaterial = false;
    this.materialForm = { id: undefined, title: '', url: '', course: { id: null } };
  }

  // --- Consolidated Management Methods ---
  saveCourse() {
    this.courseService.createCourse(this.newCourse as any).subscribe(() => this.refreshAllData());
  }
  saveOffering() {
    this.offeringService
      .createOffering(this.newOffering as any)
      .subscribe(() => this.refreshAllData());
  }
  saveTask() {
    this.taskService.createTask(this.newTask as any).subscribe(() => alert('Task Published'));
  }
  saveAnnouncement() {
    this.announcementService
      .createAnnouncement(this.newAnnouncement as any)
      .subscribe(() => alert('Announced'));
  }
  enrollStudent(studentId: number, offeringId: number) {
    this.registrationService
      .createRegistration({
        studentId,
        offering: { id: offeringId } as any,
      })
      .subscribe(() => this.checkEnrollmentStatus());
  }
  checkEnrollmentStatus() {
    if (this.selectedOfferingId) {
      this.registrationService
        .getRegistrations({ offeringId: this.selectedOfferingId })
        .subscribe(
          (registrations) =>
            (this.enrolledStudentIds = registrations.map((registration) => registration.studentId)),
        );
    }
  }
  saveTeamGroup() {
    this.teamGroupService
      .createGroup(this.newTeamGroup as any)
      .subscribe(() => alert('Group Created'));
  }
}
