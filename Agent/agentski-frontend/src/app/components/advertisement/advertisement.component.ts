import { Component, OnInit } from '@angular/core';
import { CarModels } from 'src/app/models/CarModels';
import { FuelType } from 'src/app/models/FuelType';
import { AdvertisementService } from 'src/app/services/advertisement.service';
import { AdminService } from 'src/app/services/admin.service';
import { first } from 'rxjs/operators';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Pricing } from 'src/app/models/Pricing';
import { Router } from '@angular/router';
//import * as moment from 'moment';
// import {ModalDismissReasons, NgbDatepickerConfig, NgbModal} from '@ng-bootstrap/ng-bootstrap';
// import {faCalendar, faWindowClose, faPlus, faMinus} from '@fortawesome/free-solid-svg-icons';
@Component({
  selector: 'app-advertisement',
  templateUrl: './advertisement.component.html',
  styleUrls: ['./advertisement.component.css']
})
export class AdvertisementComponent implements OnInit {
  fuelType: FuelType[];
  nameAdvertisement: string;
  milage: number;
  
  ft:FuelType;
  
  carModels:CarModels[];
  cm:CarModels;
  pricings:Pricing[];
  pr:Pricing;

  //minDate = moment(new Date()).format('YYYY-MM-DD');  //current
  constructor(
    private adminService: AdminService, 
    private advertisementService: AdvertisementService,
    private router: Router,
  ) {
  }
  selectedFuelType(name:FuelType){
    this.ft = name;
    console.log(this.fuelType);
  }
  selectedCarModel(name:CarModels){
    this.cm = name;
    console.log(this.carModels);
  }

  selectedPricing(name:Pricing){
    this.pr = name;
    console.log(this.pricings);
  }
  ngOnInit(): void {
    // this.adminService.getCarModel().subscribe(data =>{
    //   this.model = data;
    // });
    this.adminService.getFuelT().subscribe(data =>{
      this.fuelType = data;
    });
    this.adminService.getCarModels().subscribe(data =>{
      this.carModels = data;
    });
    this.advertisementService.getPricing().subscribe(data =>{
      this.pricings = data;
    });
  }
  onSubmit() {
    console.log(this.ft);
    console.log(this.cm);

    console.log(this.pr);

    console.log(this.pr.name,this.cm.name, this.ft.name, this.milage, this.nameAdvertisement );
    
    this.advertisementService.addCar(this.pr.name, this.cm.name, this.ft.name, this.milage, this.nameAdvertisement,).pipe(first())
    .subscribe(
        data => {
            console.log('Making advertisement successful');
          
           
        })

  }

  onPricing() {
    this.router.navigateByUrl('pricing');

  }
}
