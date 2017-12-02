import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, Route, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { SingleDetailComponent } from './';

export const SINGLE_DETAIL_ROUTE: Route = {
  path: 'missing-person-detail/:id',
  component: SingleDetailComponent,
  data: {
    pageTitle: 'Missing Person'
  },
  canActivate: [UserRouteAccessService]
};
