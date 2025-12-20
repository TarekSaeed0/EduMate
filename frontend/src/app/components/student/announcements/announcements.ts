import { Component, OnInit } from '@angular/core';
import { AnnouncementService } from '../../../services/announcement.service';
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
  announcements: AnnouncementDTO[] = [];
  showModal: boolean = false;

  newPost: AnnouncementDTO = {
    title: '',
    content: '',
    scopeType: 'COURSE',
    scopeId: 0
  };

  constructor(private announcementService: AnnouncementService) {}

  ngOnInit(): void {
    this.loadAnnouncements();
  }

  loadAnnouncements(): void {
    this.announcementService.getAnnouncements().subscribe({
      next: (data) => this.announcements = data,
      error: (err) => console.error('Error loading data', err)
    });
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
