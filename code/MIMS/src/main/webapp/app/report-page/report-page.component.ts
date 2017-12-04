import { Component, OnInit, OnDestroy } from '@angular/core';
import { Http, Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';

import { Report } from '../entities/report/report.model';

import { Observable } from 'rxjs/Rx';
import { Subscription } from 'rxjs/Rx';
import { ReportService } from '../entities/report/report.service';
import { ITEMS_PER_PAGE, ResponseWrapper, createRequestOption } from '../shared';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import * as jsPDF from 'jspdf'

@Component({
  selector: 'jhi-report-page',
  templateUrl: './report-page.component.html',
  styleUrls: [
    'report-page.css'
  ]
})
export class ReportPageComponent implements OnInit, OnDestroy {

  reports: Report[];
  isSaving: boolean;
  addOpen: boolean[];
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
    private eventManager: JhiEventManager,
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
    this.isSaving = false;
    this.loadReportsForUser();
    this.registerChangeInReports();
    this.addOpen = new Array();
    for (let i = 0; i < this.addOpen.length; i++) {
      this.addOpen[i] = false;
    }
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
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

close(i: number) {
  if (confirm('Are you sure to close this report?')) {
    this.isSaving = true;
    this.reports[i].status = 'closed';
    this.fixDates(this.reports[i]);
    this.subscribeToSaveResponse(
      this.reportService.update(this.reports[i]));
    }
}

save(i: number) {
  this.isSaving = true;
  this.reports[i].status = 'active';
  this.fixDates(this.reports[i]);
  this.subscribeToSaveResponse(
    this.reportService.update(this.reports[i]));
  this.addOpen[i] = false;
}

private subscribeToSaveResponse(result: Observable<Report>) {
  result.subscribe((res: Report) =>
      this.onSaveSuccess(res), (res: Response) => this.onSaveError());
}

private onSaveSuccess(result: Report) {
  this.eventManager.broadcast({ name: 'reportListModification', content: 'OK'});
  this.isSaving = false;
}

private onSaveError() {
  this.isSaving = false;
}

registerChangeInReports() {
  this.eventSubscriber = this.eventManager.subscribe('reportListModification', (response) => this.loadReportsForUser());
}

private fixDates(report: Report) {
  if (report.dateOfBirth !== null) {
    report.dateOfBirth.year = report.dateOfBirth.getFullYear();
    report.dateOfBirth.month = report.dateOfBirth.getMonth() + 1;
    report.dateOfBirth.day = report.dateOfBirth.getDate();
  }
  if (report.lastSeen !== null) {
    report.lastSeen.setHours( report.lastSeen.getHours() + (report.lastSeen.getTimezoneOffset() / - 60) );
    report.lastSeen = report.lastSeen.toJSON();
  }
  if (report.createdAt !== null) {
    report.createdAt.setHours( report.createdAt.getHours() + (report.createdAt.getTimezoneOffset() / - 60) );
    report.createdAt = report.createdAt.toJSON();
  }
  if (report.updatedAt !== null) {
    report.updatedAt.setHours( report.updatedAt.getHours() + (report.updatedAt.getTimezoneOffset() / - 60) );
    report.updatedAt = report.updatedAt.toJSON();
  }
}

download(report: Report) {

    const doc = new jsPDF();
    doc.text(10, 20, 'Victim Information');
    doc.text(20, 30, 'Name: ' + report.victimName);
    doc.text(20, 40, 'Phone Number: ' + report.victimPhoneNumber);
    doc.text(20, 50, 'Email: ' + report.victimEmail);
    doc.text(20, 60, 'Date of birth: ' + report.dateOfBirth);
    doc.text(20, 70, 'Height: ' + report.height);
    doc.text(20, 80, 'Eye color: ' + report.eyeColor);
    doc.text(20, 90, 'Demographic: ' + report.demographic);

    doc.text(10, 110, 'Guardian Information');
    doc.text(20, 120, 'Name: ' + report.parentName);
    doc.text(20, 130, 'Phone Number: ' + report.parentPhoneNumber);
    doc.text(20, 140, 'Email: ' + report.parentEmail);

    doc.text(10, 160, 'Last Seen Detail');
    doc.text(20, 170, 'Last known location: ' + report.lastKnownLocation);
    doc.text(20, 180, 'Last seen: ' + report.lastSeen);

    doc.text(10, 200, 'Phone Service');
    doc.text(20, 210, 'Serivce provider: ' + report.serviceProvider);
    doc.text(20, 220, 'Account number: ' + report.serviceProviderAccountNumber);

    if (report.additionalInformation != null) {
      doc.text(10, 240, 'Additional Information');
      doc.text(20, 250, report.additionalInformation);
    }
    // Save the PDF
    doc.save('Report.pdf');
}

}
