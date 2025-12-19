import { TestBed } from '@angular/core/testing';

import { TeamJoinRequestService } from './team-join-request.service';

describe('TeamJoinRequestService', () => {
  let service: TeamJoinRequestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TeamJoinRequestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
