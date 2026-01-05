import { Routes } from '@angular/router';
import { Home } from './components/home/home';
import { Signup } from './components/signup/signup';
import { Signin } from './components/signin/signin';
import { StudentTasks } from './components/student/tasks/tasks';
import { FAQComponent } from './components/student/faq/faq.component';
import { TeamCreator } from './components/student/team-creator/team-creator';
import { AnnouncementsComponent } from './components/student/announcements/announcements';
import { MaterialsComponent } from './components/student/materials/materials';
import { TimetableComponent } from './components/student/timetable/timetable';
import { AdminDashboard } from './components/admin-dashboard/admin-dashboard';
import { Map } from './components/map/map';
import { TeamsListComponent } from './components/student/teams-list/teams-list';
import { TeamDetailsComponent } from './components/student/team-details/team-details';
import { PageNotFound } from './components/page-not-found/page-not-found';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: Home, title: 'Home' },
  { path: 'signup', component: Signup, title: 'Sign Up' },
  { path: 'signin', component: Signin, title: 'Sign In' },
  { path: 'student/tasks', component: StudentTasks, title: 'Tasks' },
  { path: 'student/faq', component: FAQComponent, title: 'FAQ' },
  { path: 'student/team-creator', component: TeamCreator, title: 'Teams' },

  { path: 'student/teams-list', component: TeamsListComponent, title: 'Find Team' },
  { path: 'student/team-details/:id', component: TeamDetailsComponent, title: 'Team Details' },

  { path: 'student/announcements', component: AnnouncementsComponent, title: 'Announcements' },
  { path: 'student/materials', component: MaterialsComponent, title: 'Materials' },
  { path: 'student/timetable', component: TimetableComponent, title: 'Time Table' },
  { path: 'student/map', component: Map, title: 'Map' },

  { path: 'admin/dashboard', component: AdminDashboard },

  { path: '**', component: PageNotFound },
];
