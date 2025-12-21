import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Navbar } from '../../navbar/navbar';
import { AnnouncementService, Announcement } from '../../../services/announcement.service';
import { CourseService } from '../../../services/course.service';
import { AuthenticationService } from '../../../services/authentication.service';

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
  private authService = inject(AuthenticationService);

  announcements: Announcement[] = [];
  courses: any[] = [];
  activeFilter: 'ALL' | 'COURSE' = 'ALL';
  selectedCourseId: number | null = null;

  // FIX: Updated role name to 'ADMINISTRATOR' as per your system
  get isAdmin(): boolean {
    const currentUser = this.authService.user();
    const result = currentUser ? currentUser.roles.includes('ADMINISTRATOR') : false;
    return result;
  }

  showEditModal: boolean = false;
  editingPost: Announcement | null = null;

  ngOnInit(): void {
    // Load the user and debug roles
    this.authService.loadUser().subscribe(user => {
      console.log('Current User Roles:', user?.roles);
    });

    this.loadAnnouncements();
    this.loadCourses();
  }

  loadAnnouncements(): void {
    this.announcementService.getAnnouncements().subscribe(data => {
      this.announcements = data;
    });
  }

  loadCourses(): void {
    this.courseService.getCourses().subscribe(data => this.courses = data);
  }

  setFilter(filter: 'ALL' | 'COURSE') {
    this.activeFilter = filter;
    if (filter === 'ALL') this.selectedCourseId = null;
  }

  get filteredAnnouncements(): Announcement[] {
    if (this.activeFilter === 'ALL') return this.announcements;
    return this.announcements.filter(a => a.scopeType === 'COURSE' && a.scopeId === this.selectedCourseId);
  }

  deletePost(id: number): void {
    if (!this.isAdmin) return;
    if (confirm('Are you sure you want to delete this announcement?')) {
      this.announcementService.deleteAnnouncement(id).subscribe(() => this.loadAnnouncements());
    }
  }

  openEditModal(post: Announcement): void {
    if (!this.isAdmin) return;
    this.editingPost = { ...post };
    this.showEditModal = true;
  }

  closeEditModal(): void {
    this.showEditModal = false;
    this.editingPost = null;
  }

  updatePost(): void {
    if (!this.isAdmin || !this.editingPost) return;

    // Clean payload for backend
    const { scope, createdAt, ...cleanPayload } = this.editingPost as any;

    this.announcementService.updateAnnouncement(this.editingPost.id, cleanPayload).subscribe({
      next: () => {
        this.loadAnnouncements();
        this.closeEditModal();
        alert('Update successful!');
      }
    });
  }
}
