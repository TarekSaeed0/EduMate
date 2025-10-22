import { Component, inject } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  ReactiveFormsModule,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { AuthenticationService, Gender } from '../../services/authentication.service';
import { Router, RouterLink } from '@angular/router';

export const passwordMatchingValidatior: ValidatorFn = (
  control: AbstractControl,
): ValidationErrors | null => {
  const password = control.get('password');
  const confirmPassword = control.get('confirmPassword');

  return password?.value === confirmPassword?.value ? null : { passwordMismatch: true };
};

@Component({
  selector: 'app-signup',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './signup.html',
  styleUrl: './signup.css',
})
export class Signup {
  private authenticationService = inject(AuthenticationService);

  private formBuilder = inject(FormBuilder);
  form = this.formBuilder.group(
    {
      id: ['', [Validators.required, Validators.pattern('^[0-9]+$')]],
      name: ['', Validators.required],
      gender: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      universityEmail: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required],
    },
    {
      validators: passwordMatchingValidatior,
    },
  );

  private router = inject(Router);

  onSubmit() {
    const value = this.form.value;
    if (
      value.id &&
      value.name &&
      value.gender &&
      value.email &&
      value.universityEmail &&
      value.password
    ) {
      this.authenticationService
        .signup({
          id: parseInt(value.id, 10),
          name: value.name,
          gender: value.gender as Gender,
          email: value.email,
          universityEmail: value.universityEmail,
          password: value.password,
        })
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
