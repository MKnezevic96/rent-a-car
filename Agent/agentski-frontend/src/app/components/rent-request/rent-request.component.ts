import { Component, OnInit } from '@angular/core';
import { Car } from 'src/app/models/Car';
import { AdvertisementService } from 'src/app/services/advertisement.service';
import { RentRequest } from 'src/app/models/RentRequest';
import { first } from 'rxjs/operators';
import { ActivatedRoute, Router } from '@angular/router';
@Component({
  selector: 'app-rent-request',
  templateUrl: './rent-request.component.html',
  styleUrls: ['./rent-request.component.css']
})
export class RentRequestComponent implements OnInit {
  cars: Car[];
  car:Car;
  selectedStartDate:Date;
  selectedEndDate: Date;
  id:number;
  rentrequest: RentRequest;
  availableCars:boolean = false;
  moreFilters:boolean = false;
  constructor(
    private advertisementService: AdvertisementService,
    private route: ActivatedRoute,
    private router: Router
    ) { }
    selectedCar(name:Car){
      this.car = name;
    }
    selectStartDate() {
    }
    selectEndDate() {
    }
  ngOnInit(): void {
    this.advertisementService.getCar().subscribe(data =>{
      this.cars = data;
    });
  }
  onSubmit() {
    //this.rentrequest={carName:this.car.name, startDate: this.selectedStartDate, endDate:this.selectedEndDate, status: false, deleted: false, id:this.id };
    this.rentrequest={id:1, carName:this.car.name, startDate: this.selectedStartDate, endDate:this.selectedEndDate, status: '', deleted: false };
    this.advertisementService.addRentRequest(this.rentrequest).pipe(first())
    .subscribe(
        data => {
        })
  }
  naKlik(carName:string) {
    this.rentrequest={carName:carName, startDate: this.selectedStartDate, endDate:this.selectedEndDate, status: '', deleted: false, id:this.id };
    this.advertisementService.addRentRequest(this.rentrequest).pipe(first())
    .subscribe(
        data => {
        })
  }

  getAvailableCars(){
    this.advertisementService.getAvailableCars(this.selectedStartDate, this.selectedEndDate).subscribe(data =>{
      this.cars = data;
    });
    this.availableCars = !this.availableCars;
  }

  filterCars(){
    this.router.navigate(['filterCars'], {relativeTo:this.route.parent});

  }
}
