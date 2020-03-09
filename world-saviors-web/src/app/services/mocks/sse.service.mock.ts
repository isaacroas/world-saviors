import { SseMessage } from '../../domain/sse-message.domain';
import { of } from 'rxjs';
import { Deed } from 'src/app/domain/deed.domain';

export class SseServiceMock {

  getSse() {
    const deeds = [
      new Deed('foo'),
      new Deed('bar')
    ];
    const sseMessage = new SseMessage(10, 20, 30, deeds);
    return of(sseMessage);
  }

}
