import { Component, OnInit } from '@angular/core';
import { RentRequest } from 'src/app/models/RentRequest';
import { AdvertisementService } from 'src/app/services/advertisement.service';

@Component({
  selector: 'app-rent-payment',
  templateUrl: './rent-payment.component.html',
  styleUrls: ['./rent-payment.component.css']
})
export class RentPaymentComponent implements OnInit {

  rentRequests:RentRequest[];

  constructor(private advertisementService: AdvertisementService) { 
    
  } 

  ngOnInit(): void {
    this.advertisementService.getApprovedRents().subscribe(data =>{
      this.rentRequests = data;
      console.log(this.rentRequests);
    });
  }

  payRent(id:number){
    this.advertisementService.payRent(id).subscribe((data)=>{
      console.log(data);
      this.advertisementService.getApprovedRents().subscribe(data =>{
        this.rentRequests = data;
        console.log(this.rentRequests);
      });
    });
  }

}
