import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MimsSharedModule } from '../../shared';
import {
    TipService,
    TipPopupService,
    TipComponent,
    TipDetailComponent,
    TipDialogComponent,
    TipPopupComponent,
    TipDeletePopupComponent,
    TipDeleteDialogComponent,
    tipRoute,
    tipPopupRoute,
} from './';

const ENTITY_STATES = [
    ...tipRoute,
    ...tipPopupRoute,
];

@NgModule({
    imports: [
        MimsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TipComponent,
        TipDetailComponent,
        TipDialogComponent,
        TipDeleteDialogComponent,
        TipPopupComponent,
        TipDeletePopupComponent,
    ],
    entryComponents: [
        TipComponent,
        TipDialogComponent,
        TipPopupComponent,
        TipDeleteDialogComponent,
        TipDeletePopupComponent,
    ],
    providers: [
        TipService,
        TipPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MimsTipModule {}
