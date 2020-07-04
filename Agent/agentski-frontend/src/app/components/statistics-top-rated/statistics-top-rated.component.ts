import { Component, OnInit } from '@angular/core';
import { AdvertisementService } from 'src/app/services/advertisement.service';
import { Car } from 'src/app/models/Car';
import { CarDetails } from 'src/app/models/CarDetails';

@Component({
  selector: 'app-statistics-top-rated',
  template: `
  <table class="table table-borderless">
  <thead>
    <tr>           
      <th>Car name</th>
      <th>Class</th>
      <th>Model</th>
      <th>Manufacturer</th>
      <th>Fuel</th>
      <th>Average rating</th>
    </tr>
  </thead>
  <tbody>
    <tr *ngFor="let car of cars">           
      <td>{{ car.carName }}</td>
      <td>{{ car.carClass }}</td>
      <td>{{ car.carModelName }}</td>
      <td>{{ car.carManufacturer }}</td>
      <td>{{ car.fuelType }}</td>
      <td>{{ car.averageRating }}</td>
    </tr>
  </tbody>
</table>
  `,
  styles: [
  ]
})
export class StatisticsTopRatedComponent implements OnInit {


  cars:CarDetails[];

  constructor(private advertisementService:AdvertisementService) { }

  ngOnInit(): void {
    this.advertisementService.getTopRatedCars().subscribe(data =>{
      this.cars = data;
    });
  }

}
