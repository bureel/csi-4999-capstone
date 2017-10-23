import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { ReportPageComponent } from './';

export const REPORT_PAGE_ROUTE: Route = {
  path: 'family-home',
  component: ReportPageComponent,
  data: {
    authorities: ['ROLE_USER'],
    pageTitle: 'report-page.title'
  },
  canActivate: [UserRouteAccessService]
};
