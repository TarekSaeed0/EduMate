import { Component, OnInit, inject, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Navbar } from '../../navbar/navbar';
import { CourseMaterialService } from '../../../services/course-material.service';
import { CourseService, Course } from '../../../services/course.service';

@Component({
  selector: 'app-materials',
  standalone: true,
  imports: [CommonModule, Navbar, FormsModule],
  templateUrl: './materials.html',
  styleUrls: ['./materials.css']
})
export class MaterialsComponent implements OnInit {
  private materialService = inject(CourseMaterialService);
  private courseService = inject(CourseService);

  // FIX: Explicitly defined to resolve TS2339
  allMaterials = signal<any[]>([]);
  coursesList = signal<Course[]>([]);
  selectedCourseId = signal<number | null>(null);

  // ID-based filtering for 100% accuracy
  filteredList = computed(() => {
    const id = this.selectedCourseId();
    return this.allMaterials().filter(m => m.course && m.course.id === id);
  });

  ngOnInit(): void {
    // Dynamically load the sidebar from the database
    this.courseService.getCourses().subscribe(courses => {
      this.coursesList.set(courses);
      if (courses.length > 0) {
        this.selectedCourseId.set(courses[0].id);
      }
    });

    this.loadData();
  }

  loadData() {
    this.materialService.getMaterials().subscribe(data => {
      this.allMaterials.set(data);
    });
  }

  selectCourse(id: number) {
    this.selectedCourseId.set(id);
  }

  onOpen(url: string) {
    if (url) window.open(url, '_blank');
  }

  onDelete(id: number) {
    if (confirm('Delete material?')) {
      this.materialService.deleteMaterial(id).subscribe(() => this.loadData());
    }
  }
}
