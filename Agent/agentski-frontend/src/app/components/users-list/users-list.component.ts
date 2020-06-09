import { Component, OnInit } from '@angular/core';
import {User} from '../../models/User';
import { AdminService } from 'src/app/services/admin.service';


@Component({
  selector: 'app-users-list',
  templateUrl: "./users-list.component.html",
  styles: [
  ]
})
export class UsersListComponent implements OnInit {


  usersBloc:User[];


  constructor(private adminService:AdminService) { }

  ngOnInit(): void {
    this.adminService.getUsers().subscribe(data =>{
      this.usersBloc = data;
    });
  }

 

blockAcc(email:string){
    this.adminService.block(email).subscribe((data:string)=>{
      console.log(data);
    });
  }

}
