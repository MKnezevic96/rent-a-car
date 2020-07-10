import { Component, OnInit } from '@angular/core';
import { AdvertisementService } from 'src/app/services/advertisement.service';
import { Car } from 'src/app/models/Car';

@Component({
  selector: 'app-my-cars',
  templateUrl: './my-cars.component.html',
  styleUrls: ['./my-cars.component.css']
})
export class MyCarsComponent implements OnInit {

  cars: Car[];
  car:Car;

  constructor(private advertisementService: AdvertisementService) { }

  ngOnInit(): void {
    this.advertisementService.getMyCars().subscribe(data =>{
      this.cars = data;
    });
  }

}
