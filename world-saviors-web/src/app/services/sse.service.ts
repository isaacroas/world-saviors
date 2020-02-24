import { Injectable, NgZone } from '@angular/core';
import { Observable } from 'rxjs';
import { SseMessage } from '../domain/sse-message.domain';

@Injectable({
  providedIn: 'root'
})
export class SseService {
  private readonly endpoint = 'http://localhost:8080/world-saviors/api/sse';

  constructor(private zone: NgZone) { }

  getSse() {
    return Observable.create(obs => {
      const eventSource = new EventSource(this.endpoint);
      eventSource.onmessage = (sse: MessageEvent) => {
        this.zone.run(() => {
          const sseMessage = JSON.parse(sse.data) as SseMessage;
          obs.next(sseMessage);
        });
      };

      eventSource.onerror = error => {
        this.zone.run(() => {
          obs.error(error);
        });
      };
    });
  }
}
