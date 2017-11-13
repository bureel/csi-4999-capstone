import {Component, OnInit, OnDestroy, Input} from '@angular/core';
import { Http, Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';

import { Report } from '../entities/report/report.model';

import { Subscription } from 'rxjs/Rx';
import { ReportService } from '../entities/report/report.service';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

@Component({
  selector: 'jhi-single-detail',
  templateUrl: './single-detail.component.html',
  styleUrls: [
    'single-detail.css'
  ]
})
export class SingleDetailComponent implements OnInit, OnDestroy {

  report: Report;
  private subscription: Subscription;
  private eventSubscriber: Subscription;

  constructor(
    private eventManager: JhiEventManager,
    private dataUtils: JhiDataUtils,
    private reportService: ReportService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit() {
    this.subscription = this.route.params.subscribe((params) => {
        this.load(params['id']);
    });
    this.registerChangeInReports();
}

  load(id) {
      this.reportService.find(id).subscribe((report) => {
          this.report = report;
      });
  }
  byteSize(field) {
      return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
      return this.dataUtils.openFile(contentType, field);
  }

  previousState() {
      window.history.back();
  }

  ngOnDestroy() {
      this.subscription.unsubscribe();
      this.eventManager.destroy(this.eventSubscriber);
  }

  registerChangeInReports() {
      this.eventSubscriber = this.eventManager.subscribe(
          'reportListModification',
          (response) => this.load(this.report.id)
      );
  }

}
