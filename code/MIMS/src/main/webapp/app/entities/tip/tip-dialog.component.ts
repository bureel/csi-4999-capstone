import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Tip } from './tip.model';
import { TipPopupService } from './tip-popup.service';
import { TipService } from './tip.service';
import { Report, ReportService } from '../report';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-tip-dialog',
    templateUrl: './tip-dialog.component.html'
})
export class TipDialogComponent implements OnInit {

    tip: Tip;
    isSaving: boolean;

    reports: Report[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private tipService: TipService,
        private reportService: ReportService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.reportService.query()
            .subscribe((res: ResponseWrapper) => { this.reports = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.tip, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
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
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackReportById(index: number, item: Report) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-tip-popup',
    template: ''
})
export class TipPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tipPopupService: TipPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tipPopupService
                    .open(TipDialogComponent as Component, params['id']);
            } else {
                this.tipPopupService
                    .open(TipDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
