import { Component, inject } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  ReactiveFormsModule,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { AuthenticationService } from '../../services/authentication.service';
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
  form = this.formBuilder.nonNullable.group(
    {
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required],
    },
    {
      validators: passwordMatchingValidatior,
    },
  );

  private router = inject(Router);

  onSubmit() {
    if (this.form.valid) {
      const value = this.form.getRawValue();
      this.authenticationService
        .signup({
          name: value.name,
          email: value.email,
          password: value.password,
        })
        .subscribe(() => this.router.navigateByUrl('/signin'));
    }
  }
}
