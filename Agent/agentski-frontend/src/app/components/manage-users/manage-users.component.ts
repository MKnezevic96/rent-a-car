import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/User';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-manage-users',
  templateUrl: './manage-users.component.html',
  styleUrls: ['./manage-users.component.css']
})
export class ManageUsersComponent implements OnInit {

  users:User[];


  constructor(private adminService:AdminService) { }

  ngOnInit(): void {
    this.adminService.getUsers().subscribe(data =>{
      this.users = data;
    });
  }

 

  advs(email:string){
    this.adminService.advertisementPrivilege(email).subscribe((data:string)=>{
    });
  }

  rrqvs(email:string){
    this.adminService.rentRequestPrivilege(email).subscribe((data:string)=>{
    });
  }


}
