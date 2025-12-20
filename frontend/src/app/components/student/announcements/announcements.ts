import { Component, OnInit, inject } from '@angular/core';
import { AnnouncementService } from '../../../services/announcement.service';
import { CourseService } from '../../../services/course.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Navbar } from '../../navbar/navbar';

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

  // Filter and Modal States
  activeFilter: 'ALL' | 'COURSE' = 'ALL';
  selectedCourseId: number | null = null;
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
    this.announcementService.getAnnouncements().subscribe({
      next: (data) => this.announcements = data,
      error: (err) => console.error('Error loading announcements', err)
    });
  }

  loadCourses(): void {
    this.courseService.getCourses().subscribe({
      next: (data) => this.courses = data,
      error: (err) => console.error('Error loading courses', err)
    });
  }

  get filteredAnnouncements(): AnnouncementDTO[] {
    if (this.activeFilter === 'ALL') {
      return this.announcements;
    }
    return this.announcements.filter(a =>
      a.scopeType === 'COURSE' && a.scopeId === this.selectedCourseId
    );
  }

  setFilter(filter: 'ALL' | 'COURSE') {
    this.activeFilter = filter;
    if (filter === 'ALL') this.selectedCourseId = null;
  }

  toggleModal(): void {
    this.showModal = !this.showModal;
  }

  submitPost(): void {
    const payload = this.newPost as any;
    this.announcementService.createAnnouncement(payload).subscribe({
      next: () => {
        this.loadAnnouncements();
        this.toggleModal();
        this.newPost = { title: '', content: '', scopeType: 'COURSE', scopeId: 0 };
      }
    });
  }

  deletePost(id?: number): void {
    if (id && confirm('Delete this announcement?')) {
      this.announcementService.deleteAnnouncement(id).subscribe(() => this.loadAnnouncements());
    }
  }
}
