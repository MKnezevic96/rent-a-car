import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-renting-menagement',
  templateUrl: './renting-menagement.component.html',
  styleUrls: ['./renting-menagement.component.css']
})
export class RentingMenagementComponent implements OnInit {

  constructor(private route: ActivatedRoute,private router: Router) {}

  ngOnInit(): void {
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

}
