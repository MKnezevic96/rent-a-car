import { Component, OnInit } from '@angular/core';
import { RentRequest } from 'src/app/models/RentRequest';
import { AdvertisementService } from 'src/app/services/advertisement.service';

@Component({
  selector: 'app-korpa',
  template: `
   <div class="container fluid">
   <ng-container>
          <table class="table table-borderless">
              <thead>
                <tr>           
                  <th>Car</th>
                  <th>Start date</th>
                  <th>End date</th>
                 
                </tr>
              </thead>
              <tbody>
                <tr *ngFor="let rr of korpa">           
                  <td>{{ rr.carName }}</td>
                  <td>{{ rr.startDate }}</td>
                  <td>{{ rr.endDate }}</td>
                  <td><button class="btn btn-primary" (click)="deleteCart(rr.id)">Delete from cart</button></td>
                 

                  <td><button class="btn btn-primary" (click)="rent(rr.id)">Make rent request</button></td>


              </tr>
              </tbody>
            </table>

      </ng-container>
   </div>
  `,
  styles: [
  ]
})
export class KorpaComponent implements OnInit {

  korpa: RentRequest[];

  constructor(private advertisementService: AdvertisementService) { }

  ngOnInit(): void {
    this.advertisementService.getCart().subscribe(data =>{
      this.korpa = data;
    });
  }

  deleteCart(id:number){
    this.advertisementService.deleteCart(id).subscribe((data:number)=>{
     this.advertisementService.getRentRequests().subscribe(data =>{
      this.korpa = data;
    });
 });
  }

rent(id:number){
//   if((<HTMLInputElement>document.getElementById("cek")).checked){
//   this.advertisementService.rentBundle(id).subscribe((data:number)=>{
//   this.advertisementService.getRentRequests().subscribe(data =>{
//     this.korpa = data;
//   });
// });
//   }
//   else {
    this.advertisementService.rent(id).subscribe((data:number)=>{
      this.advertisementService.getRentRequests().subscribe(data =>{
        this.korpa = data;
      });
    });
  }

  }
//}
