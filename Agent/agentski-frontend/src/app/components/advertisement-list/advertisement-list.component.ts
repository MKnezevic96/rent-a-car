import { Component, OnInit } from '@angular/core';
import { AdvertisementService } from 'src/app/services/advertisement.service';
import { Car } from 'src/app/models/Car';
import { ActivatedRoute, Router } from '@angular/router';
import { CarDetails } from 'src/app/models/CarDetails';
import { Review } from 'src/app/models/Review';
import { UserService } from 'src/app/security/user.service';
import { User } from 'src/app/models/User';

@Component({
  selector: 'app-advertisement-list',
  template: `
  <ng-container *ngIf="ads" >

  <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item active">
                            <a class="nav-link" >Home <span class="sr-only">(current)</span></a>
                        </li>
                    </ul>
                </div>
                <button class="btn btn-primary" style="margin-top: 0 !important;" (click)="logout()">Log out</button>
            </nav>

   
  <div class="row">
  <div class="col-sm-4" *ngFor="let car of cars">
  <div class="card" style="width: 18rem;    margin-bottom: 10px;
  margin-top: 10px;">
  <img class="card-img-top" src="..." alt="Card image cap">
  <div class="card-body">
    <h5 class="card-title">{{car.carName}}</h5>
    <p>Model: {{car.model}}</p>
    <p>Town: {{car.town}} </p>
    <button class="btn btn-primary" (click)="viewDetails(car)">View details</button>
  </div>
</div>
  </div>
 </div>
</ng-container>


<ng-container *ngIf="details">
<div class="container fluid">
<div class="row">
  <div class="col-sm-5">
   <div class="card" style="margin: 30px 0px 30px 0px;" >

<div class="card-header" id="addNew"> Car details </div>
      <div class="card-body">

<p> Car name: {{ carName }} </p>
<p> Car class: {{ carClass }}</p>
<p> Model name: {{ carModelName }}</p>
<p> Manufacturer: {{ carManufacturer }} </p>
<p> Fuel type: {{ fuelType }}</p>
<p> Transmission type: {{ carTransmission }}</p>
<p> Price per day: {{ pricePerDay }}$</p>
<p> Mileage: {{ milage }} </p>
<p> Distance limit: {{ distanceLimit }}</p>
<p> Distance penalty: {{ distancePenalty }}</p>
<p> Discount percent: {{ doscountPercent }}%</p>
<p> Discount days: {{ discoundDays }}</p>
<p> Collision damage: {{ collisionDamage }}</p>
<p> Town: {{ town }}</p>
<p> </p>
<p> </p>
<p> </p>
<p> Owner email: {{ carOwnerEmail }}</p>
<p> Average rating: {{ averageRating }}</p>


</div>

<div class="card-header" id="addNew"> Add review </div>
<div class="card-body">

    <div id="addReview">
    <div *ngIf="ratingInput" >
        <label>Rate this car: </label>
        <input class="form-control form-control-sm" type="number" min="1" max="5" id="rating" name="rating" [(ngModel)]="rating"><p></p>
    </div>
        <label>Leave your comment: </label>
        <textarea class="form-control form-control-sm"  id="comment" name="comment" [(ngModel)]="comment" rows="7"></textarea><p></p>
      <button class="btn btn-primary" (click)="addComment()">Submit</button>
    </div>
    </div>

    <table class="table">
       <thead>
         <tr>
         <th> User </th>
         <th> Rating </th>
         <th> Comment </th>           
           
         </tr>
       </thead>
       <tbody>
         <tr *ngFor="let review of reviews">           
           <td> {{ review.userEmail }}</td>
           <td> {{ review.rating }}</td>
           <td> {{ review.review }}</td>
       </tr>
       </tbody>
     </table>


</div>
</div>
</div>
</div>
  `,
  styles: [
  ]
})
export class AdvertisementListComponent implements OnInit {

  currentUser: User;


  cars:Car[];
  reviews:Review[];
  ads:boolean = true;
  details:boolean = false;
  ratingInput:boolean = true;
  
  myData:CarDetails;

  carClass:string;
  carId:number;
  carManufacturer:string;
  carModelName:string;
  carName:string;
  carTransmission:string;
  collisionDamage:number;
  discoundDays:number;
  distanceLimit:number;
  distancePenalty:number;
  doscountPercent:number;
  fuelType:string;
  milage:number;
  ownerId:number;
  pricePerDay:number;
  pricingId:number;
  pricingPlan:string;
  town:string;
  carOwnerEmail:string;
  averageRating:string;

  rating:number;
  comment:string;

  constructor(private advertisementService:AdvertisementService, private userService: UserService, private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.advertisementService.getCar().subscribe(data => {
      this.cars = data;
    });
    
  }

  viewDetails(car:Car){
    this.advertisementService.getCarDetails(car.id).subscribe(data => {

      this.myData = data;
      this.ads=false;
      this.details=true;;
      this.carClass=this.myData.carClass;
      this.carManufacturer=this.myData.carManufacturer;
      this.carModelName=this.myData.carModelName;
      this.carTransmission=this.myData.carTransmission;
      this.pricePerDay=this.myData.pricePerDay;
      this.town=this.myData.town;
      this.fuelType=this.myData.fuelType;
      this.doscountPercent=this.myData.doscountPercent;
      this.milage=this.myData.milage;
      this.distanceLimit=this.myData.distanceLimit;
      this.discoundDays=this.myData.discoundDays;
      this.distancePenalty=this.myData.distancePenalty;
      this.carName=this.myData.carName;
      this.collisionDamage=this.myData.collisionDamage;
      this.carOwnerEmail=this.myData.carOwnerEmail;
      this.averageRating=this.myData.averageRating;
    });


    this.advertisementService.getCarReviews(car.id).subscribe(data => {
        this.reviews = data;
    })

   
    this.advertisementService.getCurrentUser().subscribe( data => {
      if(data.email === this.carOwnerEmail){
        this.ratingInput = false;
      }
    })
    
  }

  addComment() {
    return this.advertisementService.addCarReview(this.myData.carId, this.rating, this.comment)
        .subscribe(res => alert('Review added successfully. Please wait for our admin team to review and approve it.'), err => {
          if(err.status == 403) {
            alert("You don't have permission for this action. Car reviews can be added only on cars you have rented and after renting date has expired.")
        }
        

        })
      }

  logout(){
    this.userService.logout().subscribe(data =>{
    });
  }

}
