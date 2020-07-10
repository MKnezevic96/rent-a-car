import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/User';
import { AdminService } from 'src/app/services/admin.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-manage-users',
  templateUrl: './manage-users.component.html',
  styleUrls: ['./manage-users.component.css']
})
export class ManageUsersComponent implements OnInit {

  users:User[];


  constructor(private adminService:AdminService, private router: Router) { }

  ngOnInit(): void {
    this.adminService.getUsers().subscribe(data =>{
      this.users = data;
    });
  }

 

  advs(email:string){
    this.adminService.advertisementPrivilege(email).subscribe((data:string)=>{
      this.router.navigate(['/adminPage'])
    });
  }

  rrqvs(email:string){
    this.adminService.rentRequestPrivilege(email).subscribe((data:string)=>{
      this.router.navigate(['/adminPage'])
    });
  }

  mssg(email:string){
    this.adminService.messagePrivilege(email).subscribe((data:string)=>{
      this.router.navigate(['/adminPage'])
    });
  }


}
