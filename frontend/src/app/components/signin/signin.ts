import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthenticationService } from '../../services/authentication.service';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-signin',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './signin.html',
  styleUrl: './signin.css',
})
export class Signin {
  private authenticationService = inject(AuthenticationService);

  private formBuilder = inject(FormBuilder);
  form = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required],
  });

  private router = inject(Router);

  onSubmit() {
    const value = this.form.value;
    if (value.email && value.password) {
      this.authenticationService
        .signin({
          email: value.email,
          password: value.password,
        })
        .subscribe({
          next: () => {
            console.log('Sign in successful');
            this.router.navigateByUrl('/home');
          },
          error: (error) => {
            console.error('Sign in failed', error);
          },
        });
    }
  }
}
