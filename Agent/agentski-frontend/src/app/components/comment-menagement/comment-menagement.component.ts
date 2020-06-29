import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/security/user.service';

@Component({
  selector: 'app-comment-menagement',
  templateUrl: './comment-menagement.component.html',
  styleUrls: []
})
export class CommentMenagementComponent implements OnInit {

  constructor(private route: ActivatedRoute,private router: Router, private userService:UserService) {}


  ngOnInit(): void {
  }

  showPendingComments(){    
    this.router.navigate(['pendingComments'], {relativeTo: this.route});
  }

  logout(){
    this.userService.logout().subscribe(data =>{
    });
  }
}
