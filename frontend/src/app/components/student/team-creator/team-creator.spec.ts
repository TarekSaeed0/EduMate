import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamCreator } from './team-creator';

describe('TeamCreator', () => {
  let component: TeamCreator;
  let fixture: ComponentFixture<TeamCreator>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TeamCreator]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TeamCreator);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
