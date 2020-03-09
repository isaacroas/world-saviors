import { Deed } from 'src/app/domain/deed.domain';
import { of } from 'rxjs';

export class DeedServiceMock {

  putDeed(newDeed: Deed) {
    return of(newDeed);
  }

}
