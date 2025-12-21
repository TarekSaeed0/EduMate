import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Navbar } from '../../navbar/navbar';
import { CourseRegistrationService, CourseRegistration } from '../../../services/course-registration.service';

interface MaterialFile {
  id: number;
  courseId: number;
  name: string;
  type: 'PDF' | 'DOC' | 'ZIP';
  size: string;
  uploadDate: string;
}

@Component({
  selector: 'app-materials',
  standalone: true,
  imports: [CommonModule, Navbar],
  templateUrl: './materials.html',
  styleUrls: ['./materials.css']
})
export class MaterialsComponent implements OnInit {
  private registrationService = inject(CourseRegistrationService);

  registrations: CourseRegistration[] = [];
  selectedCourseId: number | null = null;

  // Hardcoded materials list
  private allMaterials: MaterialFile[] = [
    { id: 1, courseId: 1, name: 'Syllabus_Fall2025.pdf', type: 'PDF', size: '1.2MB', uploadDate: '2025-09-01' },
    { id: 2, courseId: 1, name: 'Lecture_Notes_Week1.pdf', type: 'PDF', size: '2.5MB', uploadDate: '2025-09-05' },
    { id: 3, courseId: 2, name: 'Project_Guidelines.doc', type: 'DOC', size: '800KB', uploadDate: '2025-09-10' },
    { id: 4, courseId: 3, name: 'Lab_Materials_All.zip', type: 'ZIP', size: '15MB', uploadDate: '2025-09-12' }
  ];

  ngOnInit(): void {
    // Fetch registered courses for the student
    this.registrationService.getRegistrations().subscribe(data => {
      this.registrations = data;
      if (data.length > 0) {
        this.selectedCourseId = data[0].offering.course.id;
      }
    });
  }

  get filteredMaterials(): MaterialFile[] {
    return this.allMaterials.filter(m => m.courseId === this.selectedCourseId);
  }

  selectCourse(id: number) {
    this.selectedCourseId = id;
  }

  onDownload(fileName: string) {
    alert(`Hardcoded Action: Downloading ${fileName}`);
  }
}
