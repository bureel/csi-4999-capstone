import {Component, OnInit, OnDestroy, Input} from '@angular/core';
import { Http, Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';

import { Report } from '../entities/report/report.model';
import { Tip } from '../entities/tip/tip.model';

import { ResponseWrapper } from '../shared';

import { Subscription, Observable } from 'rxjs/Rx';
import { TipService } from '../entities/tip/tip.service';
import { ReportService } from '../entities/report/report.service';
import { JhiEventManager, JhiDataUtils, JhiAlertService } from 'ng-jhipster';

@Component({
  selector: 'jhi-public-tip',
  templateUrl: './public-tip.component.html',
  styleUrls: [
    'public-tip.css'
  ]
})
export class PublicTipComponent implements OnInit {

  report: Report;
  tip: Tip;
  isSaving: boolean;
  accepted: boolean;
  private subscription: Subscription;
  private eventSubscriber: Subscription;

  constructor(
    private tipService: TipService,
    private reportService: ReportService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private dataUtils: JhiDataUtils,
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  ngOnInit() {
    this.accepted = false;
    this.subscription = this.route.params.subscribe((params) => {
        this.load(params['id']);
    });
    this.tip = new Tip();
    this.isSaving = false;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    accept() {
        this.accepted = true;
    }

    load(id) {
        this.reportService.find(id).subscribe((report) => {
            this.report = report;
        });
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    save() {
        this.isSaving = true;
        this.tip.report = this.report;
        if (this.tip.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tipService.update(this.tip));
        } else {
            this.subscribeToSaveResponse(
                this.tipService.create(this.tip));
        }
    }

    private subscribeToSaveResponse(result: Observable<Tip>) {
        result.subscribe((res: Tip) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Tip) {
        this.eventManager.broadcast({ name: 'tipListModification', content: 'OK'});
        this.isSaving = false;
        this.router.navigate(['/']);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
