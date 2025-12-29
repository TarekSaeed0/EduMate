import { Component, computed, inject } from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';
import { RouterLink } from '@angular/router';
import { Navbar } from '../navbar/navbar';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [Navbar, CommonModule, RouterLink],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
  authenticationService = inject(AuthenticationService);

  greeting = computed(() =>
    this.authenticationService.user()?.student
      ? `Hello, ${this.authenticationService.user()?.student!.name.split(' ')[0]}!`
      : 'Hello!',
  );
}
