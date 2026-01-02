import { Component, computed, inject } from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';
import { RouterLink } from '@angular/router';
import { Navbar } from '../navbar/navbar';
import { CommonModule } from '@angular/common';

interface Feature {
  icon: string;
  color: string;
  title: string;
  description: string;
  link: string;
}

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [Navbar, CommonModule, RouterLink],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
  authenticationService = inject(AuthenticationService);

  protected readonly features: Feature[] = [
    {
      icon: 'calendar_month',
      color: 'blue',
      title: 'Time Table',
      description: 'Track your classes and daily schedule.',
      link: '/student/timetable',
    },
    {
      icon: 'checklist',
      color: 'teal',
      title: 'Task Tracker',
      description: 'Organize assignments and stay productive.',
      link: '/student/tasks',
    },
    {
      icon: 'campaign',
      color: 'orange',
      title: 'Announcements',
      description: 'Never miss important updates.',
      link: '/student/announcements',
    },
    {
      icon: 'groups',
      color: 'purple',
      title: 'Team Creator',
      description: 'Create groups and collaborate smoothly.',
      link: '/student/team-creator',
    },
    {
      icon: 'menu_book',
      color: 'green',
      title: 'Material Sources',
      description: 'Access your study materials quickly.',
      link: '/student/materials',
    },
    {
      icon: 'map',
      color: 'pink',
      title: 'Campus Map',
      description: 'Navigate the campus effortlessly.',
      link: '/student/map',
    },
    {
      icon: 'help',
      color: 'lime',
      title: 'FAQ',
      description: 'Find instant answers to common questions.',
      link: '/student/faq',
    },
  ];

  greeting = computed(() =>
    this.authenticationService.user()?.student
      ? `Hello, ${this.authenticationService.user()?.student!.name.split(' ')[0]}!`
      : 'Hello!',
  );

  universityName = computed(() => this.authenticationService.user()?.student?.university.name);

  semester = computed(() => {
    const semester = this.authenticationService.user()?.student?.university.currentSemester;
    if (!semester) {
      return null;
    }

    const term = semester.term.charAt(0).toUpperCase() + semester.term.slice(1).toLowerCase();

    const year = semester.year;

    return `${term} ${year}-${year + 1}`;
  });
}
