import { Component, OnInit } from '@angular/core';
import { RentRequest } from 'src/app/models/RentRequest';
import { AdvertisementService } from 'src/app/services/advertisement.service';
import { Receipt } from 'src/app/models/Receipt';

@Component({
  selector: 'app-rent-payment',
  templateUrl: './rent-payment.component.html',
  styleUrls: ['./rent-payment.component.css']
})
export class RentPaymentComponent implements OnInit {


  receipts:Receipt[]
;
  constructor(private advertisementService: AdvertisementService) { 
    
  } 

  ngOnInit(): void {
    this.advertisementService.getReceipts().subscribe(data =>{
      this.receipts = data;
    });
  }

  payRent(id:number){
    this.advertisementService.payRent(id).subscribe((data)=>{
      this.advertisementService.getReceipts().subscribe(data =>{
        this.receipts = data;
      });
    });
  }

}
