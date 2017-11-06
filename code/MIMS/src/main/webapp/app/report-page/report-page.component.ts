import {Component, OnInit} from '@angular/core';
import { Http, Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';

import { Report } from '../entities/report/report.model';

import { Observable } from 'rxjs/Rx';
import { Subscription } from 'rxjs/Rx';
import { ReportService } from '../entities/report/report.service';
import { ITEMS_PER_PAGE, ResponseWrapper, createRequestOption } from '../shared';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

@Component({
  selector: 'jhi-report-page',
  templateUrl: './report-page.component.html',
  styleUrls: [
    'report-page.css'
  ]
})
export class ReportPageComponent implements OnInit {

  reports: Report[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  queryCount: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;

  constructor(
    private reportService: ReportService,
    private activatedRoute: ActivatedRoute,
    private parseLinks: JhiParseLinks,
    private jhiAlertService: JhiAlertService,
    private http: Http
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe((data) => {
        this.page = data['pagingParams'].page;
        this.previousPage = data['pagingParams'].page;
        this.reverse = data['pagingParams'].ascending;
        this.predicate = data['pagingParams'].predicate;
    });
  }

  ngOnInit() {
    this.loadReportsForUser()
  }

  loadReportsForUser() {
    this.reportService.queryForUser({
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort()}).subscribe(
        (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
        (res: ResponseWrapper) => this.onError(res.json)
    );
}

sort() {
  const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
  if (this.predicate !== 'id') {
      result.push('id');
  }
  return result;
}

  private onSuccess(data, headers) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = headers.get('X-Total-Count');
    this.queryCount = this.totalItems;
    // this.page = pagingParams.page;
    this.reports = data;
}
private onError(error) {
    this.jhiAlertService.error(error.message, null, null);
}

}
