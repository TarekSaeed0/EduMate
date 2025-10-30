import { Component, ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [],
  templateUrl: './navbar.html',
  styleUrls: ['./navbar.css', './sidebar.css', './profile.css']
})
export class Navbar {

  constructor(private cdr: ChangeDetectorRef) {}

  ngAfterViewInit() {
    const menuBtn = document.querySelector('.main-menu');
    const sidebar = document.querySelector('.sidebar');
    const overlay = document.querySelector('.overlay');

    if (!menuBtn || !sidebar) return;

    menuBtn?.addEventListener('click', (event) => {
      event?.stopPropagation();
      sidebar?.classList.toggle('active');
      overlay?.classList.toggle('active');
      this.cdr.detectChanges();
    });
    
    document.addEventListener('click', (event) => {
      const target = event.target as HTMLElement;

      if (!(sidebar?.contains(target)) && !(target.closest('.main-menu'))) {
        sidebar?.classList.remove('active');
        overlay?.classList.remove('active');
        this.cdr.detectChanges();
      }
    });
  }
}
