import { Component, computed, inject } from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';
import { RouterLink } from '@angular/router';
import { Navbar } from '../navbar/navbar';

@Component({
  selector: 'app-home',
  imports: [RouterLink, Navbar],
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

  signout() {
    this.authenticationService.signout().subscribe();
  }
}
