import {Component, OnInit, OnDestroy, Input} from '@angular/core';
import { Http, Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';

import { Report } from '../entities/report/report.model';
import { VictimPhoto } from '../entities/victim-photo/victim-photo.model';

import { ResponseWrapper } from '../shared';

import { Subscription } from 'rxjs/Rx';
import { ReportService } from '../entities/report/report.service';
import { VictimPhotoService } from '../entities/victim-photo/victim-photo.service';
import { JhiEventManager, JhiDataUtils, JhiAlertService } from 'ng-jhipster';

@Component({
  selector: 'jhi-single-detail',
  templateUrl: './single-detail.component.html',
  styleUrls: [
    'single-detail.css'
  ]
})
export class SingleDetailComponent implements OnInit, OnDestroy {

  victimPhotos: VictimPhoto[];
  report: Report;
  private subscription: Subscription;
  private eventSubscriber: Subscription;

  constructor(
    private victimPhotoService: VictimPhotoService,
    private jhiAlertService: JhiAlertService,
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
      this.victimPhotoService.query().subscribe(
        (res: ResponseWrapper) => {
            this.victimPhotos = res.json;
        },
        (res: ResponseWrapper) => this.onError(res.json)
    );
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

  private onError(error) {
    this.jhiAlertService.error(error.message, null, null);
}

}
