import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'jhi-report-page',
  templateUrl: './report-page.component.html',
  styleUrls: [
    'report-page.css'
  ]
})
export class ReportPageComponent implements OnInit {

  message: string;

  constructor() {
    this.message = 'ReportPageComponent message';
  }

  ngOnInit() {
  }

  onClick() {

  }
}
