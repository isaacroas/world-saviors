import { TestBed } from '@angular/core/testing';

import { SaviorsService } from './saviors.service';

describe('SaviorsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SaviorsService = TestBed.get(SaviorsService);
    expect(service).toBeTruthy();
  });
});
