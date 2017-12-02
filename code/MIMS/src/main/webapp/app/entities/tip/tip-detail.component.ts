import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Tip } from './tip.model';
import { TipService } from './tip.service';

@Component({
    selector: 'jhi-tip-detail',
    templateUrl: './tip-detail.component.html'
})
export class TipDetailComponent implements OnInit, OnDestroy {

    tip: Tip;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private tipService: TipService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTips();
    }

    load(id) {
        this.tipService.find(id).subscribe((tip) => {
            this.tip = tip;
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

    registerChangeInTips() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tipListModification',
            (response) => this.load(this.tip.id)
        );
    }
}
