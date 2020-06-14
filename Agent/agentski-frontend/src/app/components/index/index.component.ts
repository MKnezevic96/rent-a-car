import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styles: [
  ]
})
export class IndexComponent implements OnInit {

  constructor(private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
  }


  showCreateReportForm() {
    this.router.navigate(['rentingReport'], {relativeTo: this.route});
  }

  showAddAdvertisementForm() {
    this.router.navigate(['advertisement'], {relativeTo: this.route});
  }

  showAll(){
    this.router.navigate(['advertisements'], {relativeTo: this.route});
  }
}
