import { DeedService } from '../services/deed.service';
import { Component, OnInit } from '@angular/core';
import { SseService } from '../services/sse.service';
import { SseMessage } from '../domain/sse-message.domain';
import { Observable } from 'rxjs';
import { Deed } from '../domain/deed.domain';
import { tap } from 'rxjs/operators';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  newDeed: Deed;
  response$: Observable<Deed>;
  sseMessage$: Observable<SseMessage>;

  constructor(private sseService: SseService,
    private deedService: DeedService) { }

  ngOnInit() {
    this.sseMessage$ = this.sseService.getSse();
    this.newDeed = new Deed();
  }

  putDeed() {
    this.response$ = this.deedService.putDeed(this.newDeed).pipe(
      tap(() => this.newDeed = new Deed())
    );
  }

}
