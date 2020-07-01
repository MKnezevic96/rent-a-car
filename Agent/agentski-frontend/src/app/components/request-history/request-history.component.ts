import { Component, OnInit } from '@angular/core';
import { RentRequest } from 'src/app/models/RentRequest';
import { AdvertisementService } from 'src/app/services/advertisement.service';

@Component({
  selector: 'app-request-history',
  template: `
  <div class="container fluid">
  <ng-container>
         <table class="table table-borderless">
             <thead>
               <tr>           
                 <th>Car</th>
                 <th>Start date</th>
                 <th>End date</th>
                 <th>Status</th>
               </tr>
             </thead>
             <tbody>
               <tr *ngFor="let rr of requests">           
                 <td>{{ rr.carName }}</td>
                 <td>{{ rr.startDateString }}</td>
                 <td>{{ rr.endDateString }}</td>
                 <td>{{ rr.status }}</td>
                 <td><button class="btn btn-primary" (click)="cancel(rr.id)">Cancel rent request</button></td>

             </tr>
             </tbody>
           </table>
     </ng-container>
  </div>
  `,
  styles: [
  ]
})
export class RequestHistoryComponent implements OnInit {

  requests: RentRequest[];

  constructor(private advertisementService: AdvertisementService) { }

  ngOnInit(): void {
    this.advertisementService.getRequestHistory().subscribe(data =>{
      this.requests = data;
    });
  }
  
  cancel(id:number){
  this.advertisementService.cancelRentRequest(id).subscribe((data:number)=>{
    this.advertisementService.getRequestHistory().subscribe(data =>{
     this.requests = data;
    });
  });

 }

}
