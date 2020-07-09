import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from 'src/app/security/user.service';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styles: [
  ]
})
export class IndexComponent implements OnInit {

  constructor(private route: ActivatedRoute,
    private userService:UserService,
    private router: Router) { }

  ngOnInit(): void {
  }

  logout(){
    this.userService.logout().subscribe(data =>{
    });
  }

  showCreateReportForm() {
    this.router.navigate(['rentingReport'], {relativeTo: this.route});
  }

  changePassword() {
    this.router.navigate(['changePassword'], {relativeTo: this.route});
  }

  showAddAdvertisementForm() {
    this.router.navigate(['advertisement'], {relativeTo: this.route});
  }

  showAll(){
    this.router.navigate(['advertisements'], {relativeTo: this.route});
  }

  showMessages(){
    this.router.navigate(['messages'], {relativeTo: this.route});
  }

  rentingMenagement(){
    this.router.navigate(['rentingMenagement'], {relativeTo: this.route});
  }

  rentVehicle(){
    this.router.navigate(['rentVehicle'], {relativeTo:this.route});
  }
  
  showRequestHistory(){
    this.router.navigate(['requestHistory'], {relativeTo: this.route});
  }

  showStatistics(){
    this.router.navigate(['statistics'], {relativeTo: this.route});
  }
}
