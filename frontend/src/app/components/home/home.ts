import { Component, computed, inject } from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';
import { Router } from '@angular/router';
import { Navbar } from '../navbar/navbar';
import { CommonModule } from '@angular/common'; // Required for @if and other logic

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [Navbar, CommonModule],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
  authenticationService = inject(AuthenticationService);
  private router = inject(Router);

  greeting = computed(() =>
    this.authenticationService.user()?.student
      ? `Hello, ${this.authenticationService.user()?.student!.name.split(' ')[0]}!`
      : 'Hello!',
  );

  // Standard router navigation used throughout the app
  navigateTo(path: string) {
    this.router.navigate([path]);
  }

  signout() {
    this.authenticationService.signout().subscribe();
  }
}
