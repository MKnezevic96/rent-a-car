import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/security/user.service';

@Component({
  selector: 'app-renting-menagement',
  templateUrl: './renting-menagement.component.html',
  styleUrls: ['./renting-menagement.component.css']
})
export class RentingMenagementComponent implements OnInit {

  constructor(private route: ActivatedRoute,private router: Router, private userService:UserService) {}

  ngOnInit(): void {
  }

  logout(){
    this.userService.logout().subscribe(data =>{
      console.log('logged out')
    });
  }

  rentPayments(){
    this.router.navigate(['rentPayment'], {relativeTo: this.route});
  }

  toAdv(){
    this.router.navigate(['advertisement'], {relativeTo: this.route});
  }

  rentRequest(){
    this.router.navigate(['rentRequest'], {relativeTo: this.route});

  }

  approveRent(){
    this.router.navigate(['rentingApprove'], {relativeTo: this.route});

  }
  
  myCars(){
    this.router.navigate(['myCars'], {relativeTo: this.route});
  }

}
