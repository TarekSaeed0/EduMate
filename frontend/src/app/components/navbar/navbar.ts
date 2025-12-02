import { Component, ChangeDetectorRef, HostListener, viewChild, ElementRef } from '@angular/core';

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

  dropdownOpen = false;

  toggleDropdown() {
    this.dropdownOpen = !this.dropdownOpen;
  }

  onProfileSelect(option: string) {
    console.log("Selected:", option);
    this.dropdownOpen = false;

    if (option === "main") {
      window.location.href = "main";
    }
    else if (option === "switch") {
      // your code here
    }
    else if (option === "logout") {
      // logout logic
    }
  }


}
