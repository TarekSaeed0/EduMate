import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Navbar } from '../../navbar/navbar';
import { AnnouncementService } from '../../../services/announcement.service';
import { CourseService } from '../../../services/course.service';

export interface AnnouncementDTO {
  id?: number;
  scopeType: string;
  scopeId: number;
  title: string;
  content: string;
  createdAt?: Date;
}

@Component({
  selector: 'app-announcements',
  standalone: true,
  imports: [CommonModule, FormsModule, Navbar],
  templateUrl: './announcements.html',
  styleUrls: ['./announcements.css']
})
export class AnnouncementsComponent implements OnInit {
  private announcementService = inject(AnnouncementService);
  private courseService = inject(CourseService);

  announcements: AnnouncementDTO[] = [];
  courses: any[] = [];
  activeFilter: 'ALL' | 'COURSE' = 'ALL';
  selectedCourseId: number | null = null;

  // This controls the visibility of the popup
  showModal: boolean = false;

  newPost: AnnouncementDTO = {
    title: '',
    content: '',
    scopeType: 'COURSE',
    scopeId: 0
  };

  ngOnInit(): void {
    this.loadAnnouncements();
    this.loadCourses();
  }

  loadAnnouncements(): void {
    this.announcementService.getAnnouncements().subscribe(data => this.announcements = data);
  }

  loadCourses(): void {
    this.courseService.getCourses().subscribe(data => this.courses = data);
  }

  setFilter(filter: 'ALL' | 'COURSE') {
    this.activeFilter = filter;
    if (filter === 'ALL') this.selectedCourseId = null;
  }

  get filteredAnnouncements(): AnnouncementDTO[] {
    if (this.activeFilter === 'ALL') return this.announcements;
    return this.announcements.filter(a => a.scopeType === 'COURSE' && a.scopeId === this.selectedCourseId);
  }

  // Method triggered by the + NEW POST button
  toggleModal(): void {
    this.showModal = !this.showModal;
  }

  submitPost(): void {
    this.announcementService.createAnnouncement(this.newPost as any).subscribe({
      next: () => {
        this.loadAnnouncements();
        this.toggleModal(); // Close modal on success
        this.newPost = { title: '', content: '', scopeType: 'COURSE', scopeId: 0 };
      }
    });
  }

  deletePost(id?: number): void {
    if (id && confirm('Delete this post?')) {
      this.announcementService.deleteAnnouncement(id).subscribe(() => this.loadAnnouncements());
    }
  }
}
