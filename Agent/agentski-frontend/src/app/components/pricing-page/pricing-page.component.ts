import { Component, OnInit } from '@angular/core';
import { Pricing } from 'src/app/models/Pricing';
import { AdvertisementService } from 'src/app/services/advertisement.service';
import { User } from 'src/app/models/User';
import { AdminService } from 'src/app/services/admin.service';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-pricing-page',
  templateUrl: './pricing-page.component.html',
  styleUrls: ['./pricing-page.component.css']
})
export class PricingPageComponent implements OnInit {

  name:string;
  distancelimit: number;
  regularprice: number;
  overuseprice: number;
  collisiondamage: number;
  discountdays: number;
  discountperc:  number;
  pricing: Pricing;
  users:User[];
  cm:User;

  constructor(
    private advertisementService: AdvertisementService, 
    private adminService: AdminService
  ) { }

  selectedUser(name:User){
    this.cm = name;
    console.log(this.users);
  }

  ngOnInit(): void {

    this.adminService.getUsers().subscribe(data =>{
      this.users = data;
    });
  }

  onSubmit() {

    this.pricing={distanceLimit: this.distancelimit, regularPrice: this.regularprice, overusePrice: this.overuseprice, collisionDamage: this.collisiondamage, discountDays: this.discountdays, discountPercent: this.discountperc, name: this.name, deleted:false, owner: this.cm.email};
    console.log('owner je ', this.cm);

    console.log('Ceo pricing ', this.pricing);
    
    this.advertisementService.addPricing(this.pricing).pipe(first())
    .subscribe(
        data => {
            console.log('Pricing made successfully');
           // this.makeAdvertisement();
        })




        
        
  }

 

}
