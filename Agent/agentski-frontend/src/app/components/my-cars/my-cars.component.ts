import { Component, OnInit } from '@angular/core';
import { AdvertisementService } from 'src/app/services/advertisement.service';
import { Car } from 'src/app/models/Car';
import { RentRequest } from 'src/app/models/RentRequest';
import { first } from 'rxjs/operators';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-my-cars',
  templateUrl: './my-cars.component.html',
  styleUrls: ['./my-cars.component.css']
})
export class MyCarsComponent implements OnInit {

  cars: Car[];
  car:Car;
  date:boolean = false;
  selectedStartDate:Date;
  selectedEndDate: Date;
  carName:string;
  rentrequest: RentRequest;


  constructor(
    private advertisementService: AdvertisementService,
    private route: ActivatedRoute,
    private router: Router
    ) { }

  ngOnInit(): void {
    this.advertisementService.getMyCars().subscribe(data =>{
      this.cars = data;
    });
  }

  selectDate(id:string){
    this.carName = id;
    this.date = !this.date;
  }

  reserve(){
    this.rentrequest={carName:this.carName, startDate: this.selectedStartDate, endDate:this.selectedEndDate, status: '', deleted: false, id:0, startDateString:"", endDateString:"" };
    this.advertisementService.makeReservation(this.rentrequest).pipe(first())
    .subscribe(
        data => {
          alert('Reservation made');
          this.router.navigateByUrl('index');
        })
  }

}
