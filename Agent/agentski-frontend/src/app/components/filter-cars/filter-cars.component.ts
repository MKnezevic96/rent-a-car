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

@Component({
  selector: 'app-filter-cars',
  templateUrl: './filter-cars.component.html',
  styleUrls: ['./filter-cars.component.css']
})
export class FilterCarsComponent implements OnInit {

  cars: Car[];
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

  constructor(
    private adminService: AdminService,
    private advertisementService: AdvertisementService,
    private route: ActivatedRoute,
    private router: Router
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
    
  }

  selectedCarModel(name:CarModels){
    this.cm = name;
    console.log(this.carModels);
  }

  filter(){

    var fuel;
    var tran;
    var manu;
    var clas;
    var model;
    if(typeof this.ft == 'undefined'){
      fuel = 'i';
    }else{
      fuel = this.ft.name;
    }
    if(typeof this.tt == 'undefined'){
      tran = 'i';
    }else{
      tran = this.tt.name;
    }
    if(typeof this.ma == 'undefined'){
      manu = 'i';
    }else{
      manu = this.ma.name;
    }
    if(typeof this.cc == 'undefined'){
      clas = 'i';
    }else{
      clas = this.cc.name;
    }
    if(typeof this.cm == 'undefined'){
      model = 'i';
    }else{
      model = this.cm.name;
    }
    this.advertisementService.getFilteredCars(fuel, tran, manu, clas, model).subscribe(data =>{
      this.cars = data;
    });
    this.filterCars = !this.filterCars;

  }


  naKlik(carName:string) {
    this.rentrequest={carName:carName, startDate: this.selectedStartDate, endDate:this.selectedEndDate, status: '', deleted: false, id:this.id };
    this.advertisementService.addRentRequest(this.rentrequest).pipe(first())
    .subscribe(
        data => {
        })
  }

}
