import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MimsSharedCommonModule } from '../shared';

import { REPORT_PAGE_ROUTE, ReportPageComponent } from './';

@NgModule({
    imports: [
      MimsSharedCommonModule,
      RouterModule.forRoot([ REPORT_PAGE_ROUTE ], { useHash: true })
    ],
    declarations: [
      ReportPageComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MimsAppReportPageModule {}
