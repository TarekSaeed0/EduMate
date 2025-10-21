import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthenticationService } from '../../services/authentication.service';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-signup',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './signup.html',
  styleUrl: './signup.css',
})
export class Signup {
  private authenticationService = inject(AuthenticationService);

  private formBuilder = inject(FormBuilder);
  form = this.formBuilder.group({
    name: ['', Validators.required],
    email: ['', Validators.required],
    universityEmail: ['', Validators.required],
    studentId: ['', Validators.required],
    password: ['', Validators.required],
  });

  private router = inject(Router);

  onSubmit() {
    const value = this.form.value;
    if (value.name && value.email && value.universityEmail && value.studentId && value.password) {
      this.authenticationService
        .signup(value.name, value.email, value.universityEmail, value.studentId, value.password)
        .subscribe({
          next: () => {
            console.log('Sign up successful');
            this.router.navigateByUrl('/signin');
          },
          error: (error) => {
            console.error('Sign up failed', error);
          },
        });
    }
  }
}
