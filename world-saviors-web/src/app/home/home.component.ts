import { Component, OnInit } from '@angular/core';
import { SaviorsService } from '../services/saviors.service';
import { SseService } from '../services/sse.service';
import { SseMessage } from '../domain/sse-message.domain';
import { Observable } from 'rxjs';
import { Savior } from '../domain/savior.domain';

@Component({
  selector: 'ws-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  savior$: Observable<Savior>;
  newSavior: Savior;
  response$: Observable<Savior>;
  sse$: Observable<SseMessage>;

  constructor(private saviorsService: SaviorsService,
    private sseService: SseService) { }

  ngOnInit() {
    this.sse$ = this.sseService.getSse();
    this.getSavior();
    this.newSavior = new Savior();
  }

  private getSavior() {
    this.savior$ = this.saviorsService.getSavior();
  }

  putSavior() {
    this.response$ = this.saviorsService.putSavior(this.newSavior);
  }

}
