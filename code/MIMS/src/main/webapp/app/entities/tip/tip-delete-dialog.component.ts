import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Tip } from './tip.model';
import { TipPopupService } from './tip-popup.service';
import { TipService } from './tip.service';

@Component({
    selector: 'jhi-tip-delete-dialog',
    templateUrl: './tip-delete-dialog.component.html'
})
export class TipDeleteDialogComponent {

    tip: Tip;

    constructor(
        private tipService: TipService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tipService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tipListModification',
                content: 'Deleted an tip'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tip-delete-popup',
    template: ''
})
export class TipDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tipPopupService: TipPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.tipPopupService
                .open(TipDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
