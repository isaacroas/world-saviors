import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Savior } from '../domain/savior.domain';

@Injectable({
  providedIn: 'root'
})
export class SaviorsService {

  private endpoint = 'http://localhost:8080/world-saviors/api/saviors';
  private readonly httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) { }

  getSavior() {
    return this.http.get<Savior>(this.endpoint);
  }

  putSavior(newSavior: Savior) {
    return this.http.put<Savior>(this.endpoint, newSavior, this.httpOptions);
  }
}
