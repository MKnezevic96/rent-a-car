import { Component, OnInit } from '@angular/core';
import { CarModels } from 'src/app/models/CarModels';
import { FuelType } from 'src/app/models/FuelType';
import { AdvertisementService } from 'src/app/services/advertisement.service';
import { AdminService } from 'src/app/services/admin.service';
import { first } from 'rxjs/operators';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Pricing } from 'src/app/models/Pricing';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/security/user.service';

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
  town: string;
  slika:string;
  image;
  ft:FuelType;

  carModels:CarModels[];
  cm:CarModels;
  pricings:Pricing[];
  pr:Pricing;
  base64textString = [];
  // base64textString:string;
  //minDate = moment(new Date()).format('YYYY-MM-DD');  //current
  constructor(
    private adminService: AdminService,
    private advertisementService: AdvertisementService,
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService
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

    var slika : string = this.base64textString.toString();
    this.advertisementService.addCar(this.pr.name, this.cm.name, this.ft.name, this.milage, this.nameAdvertisement, this.town, this.base64textString.toString()).pipe(first())
    .subscribe(
        data => {

        },error =>{
          if(error.status == 400){
            alert('You already user maximum amount of advertisements');
          }
          if(error.status == 403){
            alert('This action has been blocked by admin');
          }
        })
        this.router.navigateByUrl('index');

  }

  onPricing() {
    // this.router.navigateByUrl('pricing');
    this.router.navigate(['pricing'], {relativeTo:this.route.parent});

  }



  logout(){
    this.userService.logout().subscribe(data =>{
    });
  }

  onUploadChange(evt: any) {
    const file = evt.target.files[0];

    if (file) {
      const reader = new FileReader();

      reader.onload = this.handleReaderLoaded.bind(this);
      reader.readAsBinaryString(file);
    }
  }

  handleReaderLoaded(e) {
    this.base64textString.push('data:image/png;base64,' + btoa(e.target.result));
    // this.base64textString = 'data:image/png;base64,' + btoa(e.target.result);
    console.log(this.base64textString);
  }
}
