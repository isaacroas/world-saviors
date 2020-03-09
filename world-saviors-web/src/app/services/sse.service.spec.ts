import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController
} from '@angular/common/http/testing';

import { SseService } from './sse.service';

describe('SseService', () => {

  let httpTestingController: HttpTestingController;
  let service: SseService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SseService],
      imports: [HttpClientTestingModule]
    });

    httpTestingController = TestBed.get(HttpTestingController);
    service = TestBed.get(SseService);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

});
