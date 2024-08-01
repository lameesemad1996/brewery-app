import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpParams} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';
import { Favorite } from '../models/favorite';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FavoriteService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  addFavorite(favorite: Favorite): Observable<Favorite> {
    return this.http.post<Favorite>(`${this.apiUrl}/favorites`, favorite).pipe(
      catchError(this.handleError)
    );
  }

  getFavorites(userId: string): Observable<Favorite[]> {
    return this.http.get<Favorite[]>(`${this.apiUrl}/favorites/${userId}`);
  }


  removeFavorite(userId: string, breweryId: string): Observable<void> {
    const params = new HttpParams().set('userId', userId).set('breweryId', breweryId);
    return this.http.delete<void>(`${this.apiUrl}/favorites`, { params }).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.status === 409) {
      errorMessage = 'Favorite already exists.';
    } else if (error.status === 404) {
      errorMessage = 'Favorite does not exist.';
    } else if (error.status === 200) {
      errorMessage = ''; // No error if status is 200
    } else {
      errorMessage = 'An unknown error occurred.';
    }
    return throwError(errorMessage);
  }
}
