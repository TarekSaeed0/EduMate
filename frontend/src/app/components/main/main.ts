import { Component, computed, inject } from '@angular/core';
import { Navbar } from '../navbar/navbar';
import { AuthenticationService } from '../../services/authentication.service';

@Component({
  selector: 'app-main',
  imports: [Navbar],
  templateUrl: './main.html',
  styleUrls: ['./main.css'],
})
export class Main {
  private authenticationService = inject(AuthenticationService);

  displayName = computed(() => `${this.authenticationService.user()?.student!.name.split(' ')[0]}`);
  displayID = computed(() => `${this.authenticationService.user()?.student!.id}`);
}
