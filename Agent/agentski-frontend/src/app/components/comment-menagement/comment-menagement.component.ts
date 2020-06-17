import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-comment-menagement',
  templateUrl: './comment-menagement.component.html',
  styleUrls: []
})
export class CommentMenagementComponent implements OnInit {

  constructor(private route: ActivatedRoute,private router: Router) {}


  ngOnInit(): void {
  }

  showPendingComments(){    
    this.router.navigate(['pendingComments'], {relativeTo: this.route});
  }
}
