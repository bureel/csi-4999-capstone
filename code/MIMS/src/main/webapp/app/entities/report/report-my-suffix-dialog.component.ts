import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ReportMySuffix } from './report-my-suffix.model';
import { ReportMySuffixPopupService } from './report-my-suffix-popup.service';
import { ReportMySuffixService } from './report-my-suffix.service';

@Component({
    selector: 'jhi-report-my-suffix-dialog',
    templateUrl: './report-my-suffix-dialog.component.html'
})
export class ReportMySuffixDialogComponent implements OnInit {

    report: ReportMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private reportService: ReportMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.report.id !== undefined) {
            this.subscribeToSaveResponse(
                this.reportService.update(this.report));
        } else {
            this.subscribeToSaveResponse(
                this.reportService.create(this.report));
        }
    }

    private subscribeToSaveResponse(result: Observable<ReportMySuffix>) {
        result.subscribe((res: ReportMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ReportMySuffix) {
        this.eventManager.broadcast({ name: 'reportListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-report-my-suffix-popup',
    template: ''
})
export class ReportMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private reportPopupService: ReportMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.reportPopupService
                    .open(ReportMySuffixDialogComponent as Component, params['id']);
            } else {
                this.reportPopupService
                    .open(ReportMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
