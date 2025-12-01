import { Component, ChangeDetectorRef, HostListener, viewChild, ElementRef, inject } from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [],
  templateUrl: './navbar.html',
  styleUrls: ['./navbar.css', './sidebar.css', './profile.css'],
})
export class Navbar {
  sidebar = viewChild.required<ElementRef<HTMLDivElement>>('sidebar');
  overlay = viewChild.required<ElementRef<HTMLSpanElement>>('overlay');

  // 1. Inject the Authentication Service and Router
  private authenticationService = inject(AuthenticationService);
  private router = inject(Router);

  constructor(private cdr: ChangeDetectorRef) {}

  toggleSidebar(event: Event) {
    event?.stopPropagation();
    this.sidebar()?.nativeElement.classList.toggle('active');
    this.overlay()?.nativeElement.classList.toggle('active');
    this.cdr.detectChanges();
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

  onProfileSelect(event: any) {
    const value = event.target.value;

    if (value === "main") {
      window.location.href = "main";
    }
    else if (value === "switch") {

    }
    else if (value === "logout") {

      this.authenticationService.signout().subscribe(() => {

        window.location.reload();
      });
    }
  }
}
