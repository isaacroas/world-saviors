import { DeedType } from '../domain/deed-type.domain';
import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Deed as Deed } from '../domain/deed.domain';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DeedService {

  private readonly endpoint = `${environment.baseUrl}deeds`;
  private readonly httpJsonOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) { }

  getDeeds() {
    return this.http.get<Array<Deed>>(this.endpoint);
  }

  putDeed(newDeed: Deed) {
    return this.http.put<Deed>(
      this.endpoint,
      newDeed,
      this.httpJsonOptions
    );
  }

  getDeedTypes() {
    return this.http.get<Array<DeedType>>(`${this.endpoint}/types`);
  }

}
