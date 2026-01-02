import { Component, ChangeDetectorRef, HostListener, inject, signal } from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';

interface DrawerOption {
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
  styleUrl: './navbar.css',
})
export class Navbar {
  private authenticationService = inject(AuthenticationService);
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

  isDrawerOpen = signal(false);

  toggleDrawer() {
    this.isDrawerOpen.set(!this.isDrawerOpen());
  }

  @HostListener('document:click', ['$event'])
  hideDrawer(event: Event) {
    const target = event.target as HTMLElement;
    if (!target.closest('#drawer, #drawer-button')) {
      this.isUserMenuOpen.set(false);
    }
  }

  isUserMenuOpen = signal(false);

  toggleUserMenu() {
    this.isUserMenuOpen.set(!this.isUserMenuOpen());
  }

  @HostListener('document:click', ['$event'])
  hideUserMenu(event: Event) {
    const target = event.target as HTMLElement;
    if (!target.closest('#user-menu, #user-menu-button')) {
      this.isUserMenuOpen.set(false);
    }
  }

  signOut() {
    this.authenticationService.signout().subscribe(() => this.router.navigateByUrl('/home'));
  }

  isAdmin(): boolean {
    return this.authenticationService.hasRole('ADMINISTRATOR');
  }
}
