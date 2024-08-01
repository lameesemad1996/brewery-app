import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Favorite } from '../models/favorite';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FavoriteService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  addFavorite(favorite: Favorite): Observable<Favorite> {
    return this.http.post<Favorite>(`${this.apiUrl}/favorites`, favorite);
  }

  getFavorites(userId: string): Observable<Favorite[]> {
    return this.http.get<Favorite[]>(`${this.apiUrl}/favorites/${userId}`);
  }

  removeFavorite(userId: string, breweryId: string): Observable<void> {
    const params = new HttpParams()
      .set('userId', userId)
      .set('breweryId', breweryId);
    return this.http.delete<void>(`${this.apiUrl}/favorites`, { params });
  }
}
