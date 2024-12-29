import { TestBed } from '@angular/core/testing';

import { TextSharingService } from './text-sharing.service';

describe('TextSharingService', () => {
  let service: TextSharingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TextSharingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
