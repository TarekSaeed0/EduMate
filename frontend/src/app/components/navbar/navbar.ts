import { Component, HostListener, inject, signal } from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';

interface DrawerOption {
  icon: string;
  color: string;
  title: string;
  link: string;
  condition?: () => boolean;
}

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {
  protected authenticationService = inject(AuthenticationService);
  private router = inject(Router);

  protected options: DrawerOption[] = [
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
      condition: this.hasRole('STUDENT'),
    },
    {
      icon: 'checklist',
      color: 'teal',
      title: 'Task Tracker',
      link: '/student/tasks',
      condition: this.hasRole('STUDENT'),
    },
    {
      icon: 'groups',
      color: 'purple',
      title: 'Team Creator',
      link: '/student/team-creator',
      condition: this.hasRole('STUDENT'),
    },
    {
      icon: 'campaign',
      color: 'orange',
      title: 'Announcements',
      link: '/student/announcements',
      condition: this.hasRole('STUDENT'),
    },
    {
      icon: 'menu_book',
      color: 'green',
      title: 'Material Sources',
      link: '/student/materials',
      condition: this.hasRole('STUDENT'),
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
    {
      icon: 'construction',
      color: 'red',
      title: 'Admin Panel',
      link: '/admin/dashboard',
      condition: this.hasRole('ADMINISTRATOR'),
    },
  ];

  isDrawerOpen = signal(false);

  toggleDrawer() {
    this.isDrawerOpen.set(!this.isDrawerOpen());
  }

  isUserMenuOpen = signal(false);

  toggleUserMenu() {
    this.isUserMenuOpen.set(!this.isUserMenuOpen());
  }

  @HostListener('document:click', ['$event'])
  hideDrawerOrUserMenu(event: Event) {
    const target = event.target as HTMLElement;

    if (!target.closest('#drawer, #drawer-button')) {
      this.isDrawerOpen.set(false);
    }

    if (!target.closest('#user-menu, #user-menu-button')) {
      this.isUserMenuOpen.set(false);
    }
  }

  signOut() {
    this.authenticationService.signout().subscribe(() => this.router.navigateByUrl('/home'));
  }

  hasRole(role: string) {
    return () => this.authenticationService.hasRole(role);
  }
}
