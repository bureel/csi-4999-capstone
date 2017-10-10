import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MimsSharedModule } from '../../shared';
import {
    ReportMySuffixService,
    ReportMySuffixPopupService,
    ReportMySuffixComponent,
    ReportMySuffixDetailComponent,
    ReportMySuffixDialogComponent,
    ReportMySuffixPopupComponent,
    ReportMySuffixDeletePopupComponent,
    ReportMySuffixDeleteDialogComponent,
    reportRoute,
    reportPopupRoute,
    ReportMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...reportRoute,
    ...reportPopupRoute,
];

@NgModule({
    imports: [
        MimsSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ReportMySuffixComponent,
        ReportMySuffixDetailComponent,
        ReportMySuffixDialogComponent,
        ReportMySuffixDeleteDialogComponent,
        ReportMySuffixPopupComponent,
        ReportMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        ReportMySuffixComponent,
        ReportMySuffixDialogComponent,
        ReportMySuffixPopupComponent,
        ReportMySuffixDeleteDialogComponent,
        ReportMySuffixDeletePopupComponent,
    ],
    providers: [
        ReportMySuffixService,
        ReportMySuffixPopupService,
        ReportMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MimsReportMySuffixModule {}
