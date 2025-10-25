import { Routes } from '@angular/router';
import { Home } from './components/home/home';
import { Signup as Signup } from './components/signup/signup';
import { Signin } from './components/signin/signin';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: Home, title: 'Home' },
  { path: 'signup', component: Signup, title: 'Sign Up' },
  { path: 'signin', component: Signin, title: 'Sign In' },
];
