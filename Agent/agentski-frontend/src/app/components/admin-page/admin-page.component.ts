import { Component, OnInit } from '@angular/core';
import { $ } from 'protractor';
import { AdminService } from 'src/app/services/admin.service';
import {CarClass} from '../../models/CarClass';
import {Manufacturer} from '../../models/Manufacturer';
import {TransmissionType} from '../../models/TransmissionType';
import {FuelType} from '../../models/FuelType';
import {CarModels} from '../../models/CarModels';
import {User} from '../../models/User';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminPageComponent implements OnInit {

  constructor(
    private adminService:AdminService,
    private route: ActivatedRoute,
    private router: Router

  ) { 
  }

  ngOnInit(): void {
   
  }


  showCodebooks() {
    this.router.navigate(['codebookMenagement'], {relativeTo: this.route});
  }

  showUsers() {
    this.router.navigate(['userMenagement'], {relativeTo: this.route});
  }

  toAdv(){
    this.router.navigateByUrl('advertisement');
  }

  rentRequest(){
    this.router.navigateByUrl('rentRequest');

  }
}
