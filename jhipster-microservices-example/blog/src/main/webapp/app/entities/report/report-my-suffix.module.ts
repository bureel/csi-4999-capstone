import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BlogSharedModule } from '../../shared';
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
        BlogSharedModule,
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
export class BlogReportMySuffixModule {}
