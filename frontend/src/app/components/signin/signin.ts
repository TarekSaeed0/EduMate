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
  form = this.formBuilder.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required],
  });

  private router = inject(Router);

  onSubmit() {
    if (this.form.valid) {
      const value = this.form.getRawValue();
      this.authenticationService
        .signin({
          email: value.email,
          password: value.password,
        })
        .subscribe(() => this.router.navigateByUrl('/home'));
    }
  }
}
