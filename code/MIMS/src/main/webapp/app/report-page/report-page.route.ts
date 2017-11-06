import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, Route, CanActivate } from '@angular/router';

import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../shared';
import { ReportPageComponent } from './';

@Injectable()
export class ReportPageResolvePagingParams implements Resolve<any> {

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

export const REPORT_PAGE_ROUTE: Route = {
  path: 'family-home',
  component: ReportPageComponent,
  resolve: {
    'pagingParams': ReportPageResolvePagingParams
},
  data: {
    authorities: ['ROLE_USER'],
    pageTitle: 'report-page.title'
  },
  canActivate: [UserRouteAccessService]
};
