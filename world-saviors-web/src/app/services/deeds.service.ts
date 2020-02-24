import { DeedType } from './../domain/deed-type.domain';
import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Deed as Deed } from '../domain/deed.domain';

@Injectable({
  providedIn: 'root'
})
export class DeedsService {
  private endpoint = 'http://localhost:8080/world-saviors/api/deeds';
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
    return this.http.get<Array<DeedType>>(this.endpoint + '/types');
  }

}
