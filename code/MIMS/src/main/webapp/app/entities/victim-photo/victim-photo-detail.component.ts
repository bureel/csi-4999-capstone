import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { VictimPhoto } from './victim-photo.model';
import { VictimPhotoService } from './victim-photo.service';

@Component({
    selector: 'jhi-victim-photo-detail',
    templateUrl: './victim-photo-detail.component.html'
})
export class VictimPhotoDetailComponent implements OnInit, OnDestroy {

    victimPhoto: VictimPhoto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private victimPhotoService: VictimPhotoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInVictimPhotos();
    }

    load(id) {
        this.victimPhotoService.find(id).subscribe((victimPhoto) => {
            this.victimPhoto = victimPhoto;
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

    registerChangeInVictimPhotos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'victimPhotoListModification',
            (response) => this.load(this.victimPhoto.id)
        );
    }
}
