import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { CreateReportComponent } from './';

export const CREATE_REPORT_ROUTE: Route = {
  path: 'create-report',
  component: CreateReportComponent,
  data: {
    authorities: ['ROLE_USER'],
    pageTitle: 'create-report.title'
  },
  canActivate: [UserRouteAccessService]
};
