import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';
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

  showComments(){
    this.router.navigate(['commentsMenagement'], {relativeTo: this.route});
  }

  rentingMenagement(){
    this.router.navigate(['rentingMenagement'], {relativeTo: this.route});
  }
}
