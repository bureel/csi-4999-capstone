import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ReportMySuffix } from './report-my-suffix.model';
import { ReportMySuffixPopupService } from './report-my-suffix-popup.service';
import { ReportMySuffixService } from './report-my-suffix.service';

@Component({
    selector: 'jhi-report-my-suffix-delete-dialog',
    templateUrl: './report-my-suffix-delete-dialog.component.html'
})
export class ReportMySuffixDeleteDialogComponent {

    report: ReportMySuffix;

    constructor(
        private reportService: ReportMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.reportService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'reportListModification',
                content: 'Deleted an report'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-report-my-suffix-delete-popup',
    template: ''
})
export class ReportMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private reportPopupService: ReportMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.reportPopupService
                .open(ReportMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
