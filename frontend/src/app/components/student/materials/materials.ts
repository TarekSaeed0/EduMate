import { Component, OnInit, inject, signal } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
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

interface Resource {
  title: string;
  link: string;
  attachment: string;
}

@Component({
  selector: 'app-materials',
  standalone: true,
  imports: [CommonModule, Navbar, FormsModule],
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

  course = signal<string>("Computer Orginazation");

  showMaterials(course: string) {

    this.course.set(course)
    switch (course) {
      case "Computer Organization":
        this.list = this.COList;
        break;

      case "Programming 2":
        this.list = this.prog2List;
        break;

      case "Discrete Structures":
        this.list = this.discreteList;
        break;

      case "Numerical Computing":
        this.list = this.numerical;
        break;

      case "Human Computer Interaction":
        this.list = this.hciList;
        break;

      case "Communication Skills":
        this.list = this.commList;
        break;

      default:
        this.list = [];
    }
}

title: string = ""
link: string = ""
attachment: string = ""

addSource(){
  const item: Resource = {
          title: this.title,
          link: this.link,
          attachment: this.attachment
        }
  switch (this.course()) {
      case "Computer Organization":
        this.COList.push(item);
        this.list = this.COList;
        break;

      case "Programming 2":
        this.prog2List.push(item);
        this.list = this.prog2List;
        break;

      case "Discrete Structures":
        this.discreteList.push(item);
        this.list = this.discreteList;
        break;

      case "Numerical Computing":
        this.numerical.push(item);
        this.list = this.numerical;
        break;

      case "Human Computer Interaction":
        this.hciList.push(item);
        this.list = this.hciList;
        break;

      case "Communication Skills":
        this.commList.push(item);
        this.list = this.commList;
        break;

      default:
        this.list = [];
  }

}

  // Correct CO list
  COList: Resource[] = [
    {
      title: "Cache Lecture",
      link: "https://drive.google.com/file/d/1JcZnJNF_sQauWR9wQkC2dEESmMDqXatt/view?usp=sharing",
      attachment: ""
    },
    {
      title: "Addressing modes Lecture",
      link: "https://drive.google.com/file/d/1Z-IUiOf5dRTiYhjun77n0mS0jUy8Dexc/view?usp=sharing",
      attachment: ""
    }
  ];

  prog2List: Resource[] = [
    {
      title: "Concurrency Design Pattern",
      link: "https://drive.google.com/file/d/195RE24isemsdoFUuSBUlPZm_0dl73b4h/view?usp=sharing",
      attachment: ""
    }
  ]

  discreteList: Resource[] = []

  numerical: Resource[] = []
  
  hciList: Resource[] = []

  commList: Resource[] = []


  list: Resource[] = this.COList;

}
