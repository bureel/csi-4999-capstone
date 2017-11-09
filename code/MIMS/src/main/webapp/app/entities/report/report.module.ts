import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MimsSharedModule } from '../../shared';
import { MimsAdminModule } from '../../admin/admin.module';
import {
    ReportService,
    ReportPopupService,
    ReportComponent,
    ReportDetailComponent,
    ReportDialogComponent,
    ReportPopupComponent,
    ReportDeletePopupComponent,
    ReportDeleteDialogComponent,
    reportRoute,
    reportPopupRoute,
    ReportResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...reportRoute,
    ...reportPopupRoute,
];

@NgModule({
    imports: [
        MimsSharedModule,
        MimsAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ReportComponent,
        ReportDetailComponent,
        ReportDialogComponent,
        ReportDeleteDialogComponent,
        ReportPopupComponent,
        ReportDeletePopupComponent,
    ],
    entryComponents: [
        ReportComponent,
        ReportDialogComponent,
        ReportPopupComponent,
        ReportDeleteDialogComponent,
        ReportDeletePopupComponent,
    ],
    providers: [
        ReportService,
        ReportPopupService,
        ReportResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MimsReportModule {}
