import { Routes } from '@angular/router';
import { BreweryListComponent } from './components/brewery-list/brewery-list.component';
import { BreweryDetailsComponent } from './components/brewery-details/brewery-details.component';
import { FavoritesComponent } from './components/favorites/favorites.component';

export const routes: Routes = [
  { path: '', component: BreweryListComponent },
  { path: 'brewery/:id', component: BreweryDetailsComponent },
  { path: 'favorites', component: FavoritesComponent }
];
