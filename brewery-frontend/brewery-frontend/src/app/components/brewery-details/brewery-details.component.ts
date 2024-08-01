import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { BreweryService } from '../../services/brewery.service';
import { Brewery } from '../../models/brewery';

@Component({
  selector: 'app-brewery-details',
  standalone: true,
  imports: [CommonModule, HttpClientModule, RouterModule],
  templateUrl: './brewery-details.component.html',
  styleUrls: ['./brewery-details.component.scss']
})
export class BreweryDetailsComponent implements OnInit {
  brewery: Brewery = {} as Brewery;

  constructor(private route: ActivatedRoute, private breweryService: BreweryService) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.breweryService.getBreweryById(id).subscribe(data => {
        this.brewery = data;
      });
    } else {
      console.error('No brewery ID provided');
    }
  }
}
