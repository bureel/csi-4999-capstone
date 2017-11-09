import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { VictimPhoto } from './victim-photo.model';
import { VictimPhotoService } from './victim-photo.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-victim-photo',
    templateUrl: './victim-photo.component.html'
})
export class VictimPhotoComponent implements OnInit, OnDestroy {
victimPhotos: VictimPhoto[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private victimPhotoService: VictimPhotoService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.victimPhotoService.query().subscribe(
            (res: ResponseWrapper) => {
                this.victimPhotos = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInVictimPhotos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: VictimPhoto) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInVictimPhotos() {
        this.eventSubscriber = this.eventManager.subscribe('victimPhotoListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
