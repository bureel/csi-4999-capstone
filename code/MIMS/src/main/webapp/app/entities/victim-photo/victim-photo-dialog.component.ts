import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { VictimPhoto } from './victim-photo.model';
import { VictimPhotoPopupService } from './victim-photo-popup.service';
import { VictimPhotoService } from './victim-photo.service';
import { Report, ReportService } from '../report';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-victim-photo-dialog',
    templateUrl: './victim-photo-dialog.component.html'
})
export class VictimPhotoDialogComponent implements OnInit {

    victimPhoto: VictimPhoto;
    isSaving: boolean;

    reports: Report[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private victimPhotoService: VictimPhotoService,
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
        this.dataUtils.clearInputImage(this.victimPhoto, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.victimPhoto.id !== undefined) {
            this.subscribeToSaveResponse(
                this.victimPhotoService.update(this.victimPhoto));
        } else {
            this.subscribeToSaveResponse(
                this.victimPhotoService.create(this.victimPhoto));
        }
    }

    private subscribeToSaveResponse(result: Observable<VictimPhoto>) {
        result.subscribe((res: VictimPhoto) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: VictimPhoto) {
        this.eventManager.broadcast({ name: 'victimPhotoListModification', content: 'OK'});
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
    selector: 'jhi-victim-photo-popup',
    template: ''
})
export class VictimPhotoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private victimPhotoPopupService: VictimPhotoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.victimPhotoPopupService
                    .open(VictimPhotoDialogComponent as Component, params['id']);
            } else {
                this.victimPhotoPopupService
                    .open(VictimPhotoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
