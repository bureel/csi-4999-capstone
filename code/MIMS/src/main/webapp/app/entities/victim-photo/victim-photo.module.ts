import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MimsSharedModule } from '../../shared';
import {
    VictimPhotoService,
    VictimPhotoPopupService,
    VictimPhotoComponent,
    VictimPhotoDetailComponent,
    VictimPhotoDialogComponent,
    VictimPhotoPopupComponent,
    VictimPhotoDeletePopupComponent,
    VictimPhotoDeleteDialogComponent,
    victimPhotoRoute,
    victimPhotoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...victimPhotoRoute,
    ...victimPhotoPopupRoute,
];

@NgModule({
    imports: [
        MimsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        VictimPhotoComponent,
        VictimPhotoDetailComponent,
        VictimPhotoDialogComponent,
        VictimPhotoDeleteDialogComponent,
        VictimPhotoPopupComponent,
        VictimPhotoDeletePopupComponent,
    ],
    entryComponents: [
        VictimPhotoComponent,
        VictimPhotoDialogComponent,
        VictimPhotoPopupComponent,
        VictimPhotoDeleteDialogComponent,
        VictimPhotoDeletePopupComponent,
    ],
    providers: [
        VictimPhotoService,
        VictimPhotoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MimsVictimPhotoModule {}
