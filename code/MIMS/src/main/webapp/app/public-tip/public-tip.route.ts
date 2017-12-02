import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, Route, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { PublicTipComponent } from './';

export const PUBLIC_TIP_ROUTE: Route = {
  path: 'submitTip/:id',
  component: PublicTipComponent,
  data: {
    pageTitle: 'Missing Person'
  },
  canActivate: [UserRouteAccessService]
};
