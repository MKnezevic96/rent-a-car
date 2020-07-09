import { Component, OnInit } from '@angular/core';
import { RentRequest } from 'src/app/models/RentRequest';
import { AdvertisementService } from 'src/app/services/advertisement.service';
import { UserService } from 'src/app/security/user.service'

@Component({
  selector: 'app-request-history',
  template: `

  <nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav">
          <li class="nav-item active">
              <a class="nav-link">Home <span class="sr-only">(current)</span></a>
          </li>
      </ul>
  </div>
  <button class="btn btn-primary" style="margin-top: 0 !important;" (click)="logout()">Log out</button>
</nav>

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

  constructor(private advertisementService: AdvertisementService, private userService:UserService) { }

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


 logout(){
  this.userService.logout().subscribe(data =>{
  });
}

}
