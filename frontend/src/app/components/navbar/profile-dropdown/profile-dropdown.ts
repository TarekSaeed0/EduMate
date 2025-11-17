import { Component, HostListener } from '@angular/core';

@Component({
  selector: 'app-profile-dropdown',
  templateUrl: './profile-dropdown.html',
  styleUrls: ['./profile-dropdown.css']
})
export class ProfileDropdownComponent {
  dropdownOpen = false;

  toggleDropdown() {
    this.dropdownOpen = !this.dropdownOpen;
  }

  // Close dropdown if click outside component
  @HostListener('document:click', ['$event'])
  onClickOutside(event: Event) {
    const target = event.target as HTMLElement;
    const clickedInside = target.closest('#profileBtn') || target.closest('#dropdownMenu');
    if (!clickedInside) {
      this.dropdownOpen = false;
    }
  }
}
