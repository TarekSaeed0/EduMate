import { TestBed } from '@angular/core/testing';

import { TeamJoinInviteService } from './team-join-invite.service';

describe('TeamJoinInviteService', () => {
  let service: TeamJoinInviteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TeamJoinInviteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
