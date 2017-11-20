import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TipComponent } from './tip.component';
import { TipDetailComponent } from './tip-detail.component';
import { TipPopupComponent } from './tip-dialog.component';
import { TipDeletePopupComponent } from './tip-delete-dialog.component';

export const tipRoute: Routes = [
    {
        path: 'tip',
        component: TipComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Tips'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tip/:id',
        component: TipDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Tips'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tipPopupRoute: Routes = [
    {
        path: 'tip-new',
        component: TipPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Tips'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tip/:id/edit',
        component: TipPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Tips'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tip/:id/delete',
        component: TipDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Tips'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
