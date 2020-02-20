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
      let isFirst = true;
      eventSource.onmessage = (sse: MessageEvent) => {
        this.zone.run(() => {
          if (isFirst) {
            isFirst = false;
          } else {
            const sseMessage = JSON.parse(sse.data) as SseMessage;
            obs.next(sseMessage);
          }
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
