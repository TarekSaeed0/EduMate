import {
  Component,
  ChangeDetectorRef,
  HostListener,
  viewChild,
  ElementRef,
  inject,
} from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';

interface Page {
  icon: string;
  color: string;
  title: string;
  link: string;
}

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './navbar.html',
  styleUrls: ['./navbar.css', './profile.css'],
})
export class Navbar {
  sidebar = viewChild.required<ElementRef<HTMLDivElement>>('sidebar');
  overlay = viewChild.required<ElementRef<HTMLSpanElement>>('overlay');

  private authenticationService = inject(AuthenticationService);
  private router = inject(Router);

  protected pages: Page[] = [
    {
      icon: 'home',
      color: 'blue',
      title: 'Home Page',
      link: '/home',
    },
    {
      icon: 'calendar_month',
      color: 'blue',
      title: 'Time Table',
      link: '/student/timetable',
    },
    {
      icon: 'checklist',
      color: 'teal',
      title: 'Task Tracker',
      link: '/student/tasks',
    },
    {
      icon: 'groups',
      color: 'purple',
      title: 'Team Creator',
      link: '/student/team-creator',
    },
    {
      icon: 'campaign',
      color: 'orange',
      title: 'Announcements',
      link: '/student/announcements',
    },
    {
      icon: 'menu_book',
      color: 'green',
      title: 'Material Sources',
      link: '/student/materials',
    },
    {
      icon: 'map',
      color: 'pink',
      title: 'Campus Map',
      link: '/student/map',
    },
    {
      icon: 'help',
      color: 'lime',
      title: 'FAQ',
      link: '/student/faq',
    },
  ];

  constructor(private cdr: ChangeDetectorRef) {}

  toggleSidebar(event: Event) {
    event?.stopPropagation();
    this.sidebar()?.nativeElement.classList.toggle('active');
    this.overlay()?.nativeElement.classList.toggle('active');
    this.cdr.detectChanges();
  }

  // Closes sidebar and navigates
  navigateTo(path: string) {
    this.router.navigate([path]);
    this.sidebar()?.nativeElement.classList.remove('active');
    this.overlay()?.nativeElement.classList.remove('active');
  }

  @HostListener('document:click', ['$event'])
  hideSidebar(event: Event) {
    const target = event.target as HTMLElement;
    if (!this.sidebar()?.nativeElement.contains(target) && !target.closest('.main-menu')) {
      this.sidebar()?.nativeElement.classList.remove('active');
      this.overlay()?.nativeElement.classList.remove('active');
      this.cdr.detectChanges();
    }
  }

  dropdownOpen = false;
  toggleDropdown() {
    this.dropdownOpen = !this.dropdownOpen;
  }

  onProfileSelect(option: string) {
    this.dropdownOpen = false;
    if (option === 'main') {
      this.navigateTo('main');
    } else if (option === 'logout') {
      this.authenticationService.signout().subscribe(() => this.navigateTo('home'));
    }
  }
  isAdmin(): boolean {
    return this.authenticationService.hasRole('ADMINISTRATOR');
  }
}
