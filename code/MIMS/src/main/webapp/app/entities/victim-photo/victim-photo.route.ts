import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { VictimPhotoComponent } from './victim-photo.component';
import { VictimPhotoDetailComponent } from './victim-photo-detail.component';
import { VictimPhotoPopupComponent } from './victim-photo-dialog.component';
import { VictimPhotoDeletePopupComponent } from './victim-photo-delete-dialog.component';

export const victimPhotoRoute: Routes = [
    {
        path: 'victim-photo',
        component: VictimPhotoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VictimPhotos'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'victim-photo/:id',
        component: VictimPhotoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VictimPhotos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const victimPhotoPopupRoute: Routes = [
    {
        path: 'victim-photo-new',
        component: VictimPhotoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VictimPhotos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'victim-photo/:id/edit',
        component: VictimPhotoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VictimPhotos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'victim-photo/:id/delete',
        component: VictimPhotoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VictimPhotos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
