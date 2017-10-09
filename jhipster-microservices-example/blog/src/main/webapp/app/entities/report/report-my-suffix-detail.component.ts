import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ReportMySuffix } from './report-my-suffix.model';
import { ReportMySuffixService } from './report-my-suffix.service';

@Component({
    selector: 'jhi-report-my-suffix-detail',
    templateUrl: './report-my-suffix-detail.component.html'
})
export class ReportMySuffixDetailComponent implements OnInit, OnDestroy {

    report: ReportMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private reportService: ReportMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInReports();
    }

    load(id) {
        this.reportService.find(id).subscribe((report) => {
            this.report = report;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInReports() {
        this.eventSubscriber = this.eventManager.subscribe(
            'reportListModification',
            (response) => this.load(this.report.id)
        );
    }
}
