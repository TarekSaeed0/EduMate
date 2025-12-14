import { TestBed } from '@angular/core/testing';

import { TeamGroupService } from './team-group.service';

describe('TeamGroupService', () => {
  let service: TeamGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TeamGroupService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
