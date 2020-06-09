import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-codebook-menagement',
  templateUrl: './codebook-menagement.component.html',
  styleUrls: []
})
export class CodebookMenagementComponent implements OnInit {

  constructor(private route: ActivatedRoute,private router: Router) {}

  ngOnInit(): void {
  }

  showCarModels(){
    this.router.navigate(['carModels'], {relativeTo: this.route});
  }

  showCarClasses(){
    this.router.navigate(['carClasses'], {relativeTo: this.route});
  }

  showManufactures(){
    this.router.navigate(['manufactures'], {relativeTo: this.route});
  }

  showFuelTypes(){
    this.router.navigate(['fuelTypes'], {relativeTo: this.route});
  }

  showTransmissionTypes(){
    this.router.navigate(['transmissionTypes'], {relativeTo: this.route});
  }

}
