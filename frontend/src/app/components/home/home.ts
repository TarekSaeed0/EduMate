import { HttpClient } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  imports: [RouterLink],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
  private httpClient = inject(HttpClient);
  baseUrl = 'http://localhost:8080/api';

  authenticationService = inject(AuthenticationService);

  greeting = '';

  constructor() {
    this.httpClient.get(`${this.baseUrl}/greeting`, { responseType: 'text' }).subscribe({
      next: (response) => {
        this.greeting = response;
      },
      error: (error) => {
        console.error('Error fetching greeting:', error);
      },
    });

    console.log(this.authenticationService.getRoles());
  }

  signout() {
    this.authenticationService.signout();
  }
}
