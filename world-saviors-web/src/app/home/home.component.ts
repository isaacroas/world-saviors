import { DeedsService } from './../services/deeds.service';
import { Component, OnInit } from '@angular/core';
import { SseService } from '../services/sse.service';
import { SseMessage } from '../domain/sse-message.domain';
import { Observable } from 'rxjs';
import { Deed } from '../domain/deed.domain';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  deeds$: Observable<Array<Deed>>;
  newDeed: Deed;
  response$: Observable<Deed>;
  sseMessage$: Observable<SseMessage>;

  constructor(private sseService: SseService,
    private deedsService: DeedsService) { }

  ngOnInit() {
    this.sseMessage$ = this.sseService.getSse();
    this.newDeed = new Deed();
  }

  putDeed() {
    this.response$ = this.deedsService.putDeed(this.newDeed);
    this.deeds$ = this.deedsService.getDeeds();
  }

}
