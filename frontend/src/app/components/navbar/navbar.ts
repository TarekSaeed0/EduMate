import {
  Component,
  ChangeDetectorRef,
  HostListener,
  viewChild,
  ElementRef,
  inject,
} from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './navbar.html',
  styleUrls: ['./navbar.css', './sidebar.css', './profile.css'],
})
export class Navbar {
  sidebar = viewChild.required<ElementRef<HTMLDivElement>>('sidebar');
  overlay = viewChild.required<ElementRef<HTMLSpanElement>>('overlay');

  private authenticationService = inject(AuthenticationService);
  private router = inject(Router);

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
