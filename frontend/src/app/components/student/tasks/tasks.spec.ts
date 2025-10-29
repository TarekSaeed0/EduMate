import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentTasks } from './tasks';

describe('StudentTasks', () => {
  let component: StudentTasks;
  let fixture: ComponentFixture<StudentTasks>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StudentTasks],
    }).compileComponents();

    fixture = TestBed.createComponent(StudentTasks);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
