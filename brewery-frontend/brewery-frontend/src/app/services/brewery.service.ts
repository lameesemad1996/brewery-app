import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
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
    return this.http.get<Brewery[]>(`${this.apiUrl}/breweries`, {
      params: new HttpParams().set('page', page.toString())
    });
  }

  searchBreweries(name: string, city: string, state: string, type: string): Observable<Brewery[]> {
    let params = new HttpParams();
    if (name) params = params.set('name', name);
    if (city) params = params.set('city', city);
    if (state) params = params.set('state', state);
    if (type) params = params.set('type', type);

    return this.http.get<Brewery[]>(`${this.apiUrl}/breweries/search`, { params });
  }

  getBreweryById(id: string): Observable<Brewery> {
    return this.http.get<Brewery>(`${this.apiUrl}/breweries/${id}`);
  }

  getBreweriesByIds(ids: string[]): Observable<Brewery[]> {
    const params = new HttpParams().set('ids', ids.join(','));
    return this.http.get<Brewery[]>(`${this.apiUrl}/breweries/by_ids`, { params });
  }
}
