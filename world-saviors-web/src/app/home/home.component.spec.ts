import { DeedService } from '../services/deed.service';
import { SseService } from './../services/sse.service';
import { DeedServiceMock } from '../services/mocks/deed.service.mock';
import { SseServiceMock } from '../services/mocks/sse.service.mock';
import { async, ComponentFixture, TestBed, fakeAsync } from '@angular/core/testing';

import { HomeComponent } from './home.component';
import { FormsModule } from '@angular/forms';
import { Deed } from '../domain/deed.domain';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;
  let sseServiceMock: SseServiceMock;
  let deedServiceMock: DeedServiceMock;

  beforeEach(async(() => {
    sseServiceMock = new SseServiceMock();
    deedServiceMock = new DeedServiceMock();
    TestBed.configureTestingModule({
      declarations: [ HomeComponent ],
      imports: [FormsModule],
      providers: [
        { provide: SseService, useValue: sseServiceMock },
        { provide: DeedService, useValue: deedServiceMock }
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should receive an SSE message', fakeAsync(() => {
    component.sseMessage$.subscribe(sseMessage => {
      expect(sseMessage.deeds.length === 2);
      expect(sseMessage.deeds[0].text === 'foo');
      expect(sseMessage.deeds[0].text === 'bar');
      expect(sseMessage.influencerFollowers === 10);
      expect(sseMessage.ufoSightings === 20);
      expect(sseMessage.leaderSupporters === 30);
    });
  }));

  it('should be able to add a deed', () => {
    component.newDeed = new Deed('Saved a cat');
    component.putDeed();
    component.response$.subscribe(addedDeed => {
      expect(addedDeed.text).toEqual('Saved a cat');
      expect(component.newDeed.text).toBeUndefined();
    });
  });

});
