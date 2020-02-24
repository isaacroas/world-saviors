import { Component, OnInit } from '@angular/core';
import { SseService } from '../services/sse.service';
import { SseMessage } from '../domain/sse-message.domain';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  sse$: Observable<SseMessage>;

  constructor(private sseService: SseService) { }

  ngOnInit() {
    this.sse$ = this.sseService.getSse();
  }

}
