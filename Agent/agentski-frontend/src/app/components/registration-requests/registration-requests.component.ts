import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/User';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-registration-requests',
  template: `
   <div class="container fluid">
   <ng-container>
          <table class="table table-borderless">
              <thead>
                <tr>           
                  <th>First name</th>
                  <th>Last name</th>
                  <th>Email</th>
                </tr>
              </thead>
              <tbody>
                <tr *ngFor="let user of usersAcc">           
                  <td>{{ user.firsname }}</td>
                  <td>{{ user.lastname }}</td>
                  <td>{{ user.email }}</td>
                  <td><button class="btn btn-primary" (click)="activateAcc(user.email)">Activate</button></td>
              </tr>
              </tbody>
            </table>
      </ng-container>
   </div>
  `,
  styles: [
  ]
})
export class RegistrationRequestsComponent implements OnInit {


  usersAcc:User[];

  constructor( private adminService: AdminService) { }

  ngOnInit(): void {
    this.adminService.getUserRequests().subscribe(data =>{
      this.usersAcc = data;
    });
  }

  activateAcc(email:string){
    this.adminService.activate(email).subscribe((data:string)=>{
      console.log(data);
    });
  }

}
