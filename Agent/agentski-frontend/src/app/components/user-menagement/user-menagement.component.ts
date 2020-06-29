import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AdminService } from 'src/app/services/admin.service';
import { User } from 'src/app/models/User';
import { UserService } from 'src/app/security/user.service';


@Component({
  selector: 'app-user-menagement',
  templateUrl: './user-menagement.component.html',
  styleUrls: []
})
export class UserMenagementComponent implements OnInit {



  usersBloc:User[];
  usersBlocked:User[];


  constructor(private route: ActivatedRoute, private userService:UserService, private router: Router, private adminService:AdminService ) { }

  ngOnInit(): void {
  }

  showUserss() {
    this.router.navigate(['users'], {relativeTo: this.route});
  }

  showRequests() {
    this.router.navigate(['requests'], {relativeTo: this.route});
  }

  showBlockedUsers() {
    this.router.navigate(['blockedUsers'], {relativeTo: this.route});
  }

  logout(){
    this.userService.logout().subscribe(data =>{
    });
  }
}
