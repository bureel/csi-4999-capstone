import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ReportMySuffixComponent } from './report-my-suffix.component';
import { ReportMySuffixDetailComponent } from './report-my-suffix-detail.component';
import { ReportMySuffixPopupComponent } from './report-my-suffix-dialog.component';
import { ReportMySuffixDeletePopupComponent } from './report-my-suffix-delete-dialog.component';

@Injectable()
export class ReportMySuffixResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const reportRoute: Routes = [
    {
        path: 'report-my-suffix',
        component: ReportMySuffixComponent,
        resolve: {
            'pagingParams': ReportMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.report.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'report-my-suffix/:id',
        component: ReportMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.report.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reportPopupRoute: Routes = [
    {
        path: 'report-my-suffix-new',
        component: ReportMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.report.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'report-my-suffix/:id/edit',
        component: ReportMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.report.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'report-my-suffix/:id/delete',
        component: ReportMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.report.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
