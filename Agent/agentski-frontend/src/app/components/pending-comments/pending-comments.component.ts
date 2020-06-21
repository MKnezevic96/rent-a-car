import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';
import { Review } from 'src/app/models/Review';

@Component({
  selector: 'app-pending-comments',
  template: `
  <div class="container fluid">
    <div class="row">
      <div class="col-sm-5">
        <div class="card" style="margin: 30px 0px 30px 0px;" >
      
       </div>
</div>

  <ng-container>
  <table class="table table-borderless">
                <thead>
                  <tr>           
                    <th>Reviewer</th>
                    <th>Rating</th>
                    <th>Review</th>
                  </tr>
                </thead>
                <tbody>
                  <tr *ngFor="let com of pendingComments">           
                    <td>{{ com.reviewerId }}</td>
                    <td>{{ com.rating }}</td>
                    <td>{{ com.review }}</td>
                    <td><button class="btn btn-primary" (click)="approveReview(com.id)">Approve</button></td>
                  </tr>
                </tbody>
              </table>
</ng-container>
  </div>
  `,
  styles: []
})
export class PendingCommentsComponent implements OnInit {

  pendingComments:Review[];

  constructor(private adminService: AdminService) { }

  ngOnInit(): void {
    this.adminService.getPendingComments().subscribe(data =>{
      this.pendingComments = data;
    });
  }

  approveReview(id:number){
    this.adminService.approve(id).subscribe((data)=>{
      console.log(data);
      this.adminService.getPendingComments().subscribe(data =>{
        this.pendingComments = data;
      });
    });
  }

}
