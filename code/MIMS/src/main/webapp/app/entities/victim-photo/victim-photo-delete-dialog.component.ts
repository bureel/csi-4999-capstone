import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { VictimPhoto } from './victim-photo.model';
import { VictimPhotoPopupService } from './victim-photo-popup.service';
import { VictimPhotoService } from './victim-photo.service';

@Component({
    selector: 'jhi-victim-photo-delete-dialog',
    templateUrl: './victim-photo-delete-dialog.component.html'
})
export class VictimPhotoDeleteDialogComponent {

    victimPhoto: VictimPhoto;

    constructor(
        private victimPhotoService: VictimPhotoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.victimPhotoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'victimPhotoListModification',
                content: 'Deleted an victimPhoto'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-victim-photo-delete-popup',
    template: ''
})
export class VictimPhotoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private victimPhotoPopupService: VictimPhotoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.victimPhotoPopupService
                .open(VictimPhotoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
