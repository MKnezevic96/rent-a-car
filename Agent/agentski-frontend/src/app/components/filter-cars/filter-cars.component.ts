import { Component, OnInit } from '@angular/core';
import { AdvertisementService } from 'src/app/services/advertisement.service';
import { AdminService } from 'src/app/services/admin.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FuelType } from 'src/app/models/FuelType';
import { CarModels } from 'src/app/models/CarModels';
import { Pricing } from 'src/app/models/Pricing';
import { CarClass } from 'src/app/models/CarClass';
import { TransmissionType } from 'src/app/models/TransmissionType';
import { Manufacturer } from 'src/app/models/Manufacturer';
import { RentRequest } from 'src/app/models/RentRequest';
import { first } from 'rxjs/operators';
import { Car } from 'src/app/models/Car';
import { RentRequestComponent } from '../rent-request/rent-request.component';
import { CarDetails } from 'src/app/models/CarDetails';

@Component({
  selector: 'app-filter-cars',
  templateUrl: './filter-cars.component.html',
  styleUrls: ['./filter-cars.component.css']
})
export class FilterCarsComponent implements OnInit {

  cars: CarDetails[];
  carsAvailable: Car[];
  carsFiltered: CarDetails[];
  fuelTypes: FuelType[];
  carModels:CarModels[];
  carClasses:CarClass[];
  tTypes:TransmissionType[];
  manufacs:Manufacturer[];
  ft:FuelType;
  cm:CarModels;
  cc:CarClass;
  ma:Manufacturer;
  tt:TransmissionType;
  filterCars:boolean = false;
  rentrequest: RentRequest;
  selectedStartDate:Date;
  selectedEndDate: Date;
  id:number;
  city:string;
  minPrice:string;
  maxPrice:string;
  minMileage:string;
  maxMileage:string;
  childSeats:string;
  mileageLimit:string;
  waiver:boolean = false;

  constructor(
    private adminService: AdminService,
    private advertisementService: AdvertisementService,
    private route: ActivatedRoute,
    private router: Router,
    //private rentRequestComponent: RentRequestComponent
  ) { }

  ngOnInit(): void {

    this.adminService.getFuelT().subscribe(data =>{
      this.fuelTypes = data;
    });

    this.adminService.getCarModels().subscribe(data =>{
      this.carModels = data;
    });

    this.adminService.getCarC().subscribe(data =>{
      this.carClasses = data;
    });

    this.adminService.getTransmis().subscribe(data =>{
      this.tTypes = data;
    });

    this.adminService.getManufac().subscribe(data =>{
      this.manufacs = data;
    });
    
    this.selectedStartDate = this.advertisementService.getSelectedStartDate();
    this.selectedEndDate = this.advertisementService.getSelectedEndDate();
    this.city = this.advertisementService.getCity();
    //this.carsAvailable = this.advertisementService.getAvailable();
    console.log(this.carsAvailable);

    if(typeof this.selectedStartDate == 'undefined' || typeof this.selectedEndDate == 'undefined'){
      alert('No selected start and end date');
      this.router.navigateByUrl('index');
    }
  }

  selectedCarModel(name:CarModels){
    this.cm = name;
  }

  selectedFuelType(name:FuelType){
    this.ft = name;
  }

  selectedTransmissionType(name:TransmissionType){
    this.tt = name;
  }

  selectedCarClass(name:CarClass){
    this.cc = name;
  }

  selectedManufacturer(name:Manufacturer){
    this.ma = name;
  }

  filter(){
   
    var fuel:string = (typeof this.ft == 'undefined') ? 'i' : this.ft.name;
    var tran:string = (typeof this.tt == 'undefined') ? 'i' : this.tt.name;
    var manu:string = (typeof this.ma == 'undefined') ? 'i' : this.ma.name;
    var clas:string = (typeof this.cc == 'undefined') ? 'i' : this.cc.name;
    var model:string = (typeof this.cm == 'undefined') ? 'i' : this.cm.name;
    this.minPrice = (typeof this.minPrice == 'undefined') ? 'i' : this.minPrice;
    this.maxPrice = (typeof this.maxPrice == 'undefined') ? 'i' : this.maxPrice;
    this.minMileage = (typeof this.minMileage == 'undefined') ? 'i' : this.minMileage;
    this.maxMileage = (typeof this.maxMileage == 'undefined') ? 'i' : this.maxMileage;
    this.childSeats = (typeof this.childSeats == 'undefined') ? 'i' : this.childSeats;
    this.mileageLimit = (typeof this.mileageLimit == 'undefined') ? 'i' : this.mileageLimit;

   this.filterCars = true;
   
   this.advertisementService.getFilteredCars(fuel, tran, manu, clas, model, this.minPrice, this.maxPrice, this.minMileage, this.maxMileage, this.childSeats, this.mileageLimit, this.waiver).subscribe(data =>{
      this.carsFiltered = data;
      this.getAvailableCars();
    });
  }
  getAvailableCars(){
    this.advertisementService.getAvailableCars(this.selectedStartDate, this.selectedEndDate, this.city).subscribe(data =>{
      this.carsAvailable = data;
      this.setCars();
    });
  }

  setCars(){
    var i;
    var j;
    var kola:CarDetails[];
    for(i = 0 ; i < this.carsFiltered.length ; i++){
      for(j = 0 ; j < this.carsAvailable.length ; j++){
        if(this.carsFiltered[i].carId == this.carsAvailable[j].id){
          kola = kola || [];
          kola.push(this.carsFiltered[i]);
        }
      }
    }
    this.cars = kola;
  
  }


  naKlik(carName:string) {
    this.rentrequest={carName:carName, startDate: this.selectedStartDate, endDate:this.selectedEndDate, status: '', deleted: false, id:this.id, startDateString:null, endDateString:null};
    this.advertisementService.addRentRequest(this.rentrequest).pipe(first())
    .subscribe(
        data => {
          alert('Request sent');
          this.router.navigateByUrl('index');
        })
  }

  sortByPriceAsc(){
    this.advertisementService.sortFilteredCars(this.cars, "asc", "price").subscribe(data =>{
      this.cars=data;
    });
  }

  sortByPriceDesc(){
    this.advertisementService.sortFilteredCars(this.cars, "desc", "price").subscribe(data =>{
      this.cars=data;
    });
  }

  sortByRatingAsc(){
    this.advertisementService.sortFilteredCars(this.cars, "asc", "rating").subscribe(data =>{
      this.cars=data;
    });
  }

  sortByRatingDesc(){
    this.advertisementService.sortFilteredCars(this.cars, "desc", "rating").subscribe(data =>{
      this.cars=data;
    });
  }

  sortByMileageAsc(){
    this.advertisementService.sortFilteredCars(this.cars, "asc", "mileage").subscribe(data =>{
      this.cars=data;
    });
  }

  sortByMileageDesc(){
    this.advertisementService.sortFilteredCars(this.cars, "desc", "mileage").subscribe(data =>{
      this.cars=data;
    });
  }
}
