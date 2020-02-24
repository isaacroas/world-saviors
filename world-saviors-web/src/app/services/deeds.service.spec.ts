import { TestBed } from '@angular/core/testing';

import { DeedsService } from './deeds.service';

describe('DeedsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: DeedsService = TestBed.get(DeedsService);
    expect(service).toBeTruthy();
  });
});
