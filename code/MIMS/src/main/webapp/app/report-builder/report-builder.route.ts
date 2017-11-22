import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, Route, CanActivate } from '@angular/router';

import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../shared';
import { ReportBuilderComponent } from './';


export const REPORTBUILDER_ROUTE: Route = {
    path: 'report-builder',
    component: ReportBuilderComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'report-builder.title'
    },
    canActivate: [UserRouteAccessService]
};