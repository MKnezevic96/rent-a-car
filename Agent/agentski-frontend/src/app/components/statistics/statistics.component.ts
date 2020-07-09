import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/security/user.service';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-statistics',
  template: `
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav">
          <li class="nav-item active">
              <a class="nav-link">Statistics <span class="sr-only">(current)</span></a>
          </li>
          <li>
            <a class="nav-link" (click)="showHighestMileage()">Highest mileage</a>
          </li>
          <li>    
            <a class="nav-link" (click)="showMostCommented()">Most commented</a>
          </li>
          <li>
            <a class="nav-link" (click)="showTopRated()">Top rated</a>
          </li>
      </ul>
  </div>
  <button class="btn btn-primary" style="margin-top: 0 !important;" (click)="logout()">Log out</button>
</nav>

<div class="container fluid">
<router-outlet></router-outlet>

 </div>
  `,
  styles: [
  ]
})
export class StatisticsComponent implements OnInit {

  constructor(private userService: UserService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
  }

  showTopRated(){
    this.router.navigate(['topRated'], {relativeTo: this.route});
  }

  showHighestMileage(){
    this.router.navigate(['highestMileage'], {relativeTo: this.route});
  }

  showMostCommented(){
    this.router.navigate(['mostCommented'], {relativeTo: this.route});
  }

  logout(){
    this.userService.logout().subscribe(data =>{
    });
  }
}
