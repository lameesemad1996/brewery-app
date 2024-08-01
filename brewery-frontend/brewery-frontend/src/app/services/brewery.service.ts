import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Brewery } from '../models/brewery';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BreweryService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getAllBreweries(page: number): Observable<Brewery[]> {
    return this.http.get<Brewery[]>(`${this.apiUrl}/breweries?page=${page}`);
  }

  searchBreweries(name: string, city: string, state: string, type: string): Observable<Brewery[]> {
    return this.http.get<Brewery[]>(`${this.apiUrl}/breweries/search?name=${name}&city=${city}&state=${state}&type=${type}`);
  }

  getBreweryById(id: string): Observable<Brewery> {
    return this.http.get<Brewery>(`${this.apiUrl}/breweries/${id}`);
  }
}
