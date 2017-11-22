import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import { Http, Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';

import { Report } from '../entities/report/report.model';
import { ReportBuilderForm } from './report-builder.form';

import { Observable } from 'rxjs/Rx';
import { Subscription } from 'rxjs/Rx';
import { ReportService } from '../entities/report/report.service';
import { ITEMS_PER_PAGE, ResponseWrapper, createRequestOption } from '../shared';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

@Component({
    selector: 'jhi-report-page',
    templateUrl: './report-builder.html',
    styleUrls: [
        'report-builder.css'
    ],
    encapsulation: ViewEncapsulation.None
})
export class ReportBuilderComponent implements OnInit {

    public static STEP_1 = 1;
    public static STEP_2 = 2;
    public static STEP_3 = 3;
    public static STEP_4 = 4;
    public static STEP_5 = 5;
    public static FINISHED = 6;


    public form: ReportBuilderForm = {
        id: null,
        status: null,
        resolution: null,
        victimName: null,
        victimPhoneNumber: null,
        victimEmail: null,
        parentName: null,
        parentPhoneNumber: null,
        parentEmail: null,
        dateOfBirth: null,
        height: null,
        weight: null,
        eyeColor: null,
        demographic: null,
        lastKnownLocation: null,
        lastSeen: null,
        serviceProvider: null,
        serviceProviderAccountNumber: null,
        complaintNumber: null,
        reportNumber: null,
        investigatorName: null,
        investigatorEmail: null,
        createdAt: null,
        updatedAt: null,
        photosContentType: null,
        photos: null,
        additionalInformation: null,
        userId: null,
    };
    reports: Report[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    public currentStep = ReportBuilderComponent.STEP_1;
    constructor(
        private reportService: ReportService,
        private activatedRoute: ActivatedRoute,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private http: Http
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            console.log(data)
            if (data['pagingParams']) {
                this.page = data['pagingParams'].page;
                this.previousPage = data['pagingParams'].page;
                this.reverse = data['pagingParams'].ascending;
                this.predicate = data['pagingParams'].predicate;
            }
        });
    }

    ngOnInit() {
        this.loadReportsForUser();
    }

    onStepSubmit() {
        // Add any logic you need while transitioning between steps here
        switch (this.currentStep) {
            case ReportBuilderComponent.STEP_1:
                this.currentStep = ReportBuilderComponent.STEP_2;
                break;
            case ReportBuilderComponent.STEP_2:
                this.currentStep = ReportBuilderComponent.STEP_3;
                break;
            case ReportBuilderComponent.STEP_3:
                this.currentStep = ReportBuilderComponent.STEP_4;
                break;
            case ReportBuilderComponent.STEP_4:
                this.currentStep = ReportBuilderComponent.STEP_5;
                break;
            case ReportBuilderComponent.STEP_5:
                this.currentStep = ReportBuilderComponent.FINISHED;
                break;
            default:
                console.error('Invalid current step ' + this.currentStep);
        }
    }

    loadReportsForUser() {
        this.reportService.queryForUser({
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()}).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.reports = data;
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

}
