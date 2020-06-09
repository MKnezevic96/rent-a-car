import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/User';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-blocked-users-list',
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
        <tr *ngFor="let user of usersBlocked">           
          <td>{{ user.firsname }}</td>
          <td>{{ user.lastname }}</td>
          <td>{{ user.email }}</td>
      </tr>
      </tbody>
    </table>
</ng-container>
</div>
  `,
  styles: [
  ]
})
export class BlockedUsersListComponent implements OnInit {

  usersBlocked:User[];
  constructor(private adminService:AdminService) { }

  ngOnInit(): void {
    this.adminService.getBlockedUsers().subscribe(data =>{
      this.usersBlocked = data;
    });
  }

}
