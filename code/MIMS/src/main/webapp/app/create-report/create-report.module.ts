import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MimsSharedCommonModule } from '../shared';

import {
  CREATE_REPORT_ROUTE,
  CreateReportComponent, } from './';

@NgModule({
    imports: [
      MimsSharedCommonModule,
      RouterModule.forRoot([ CREATE_REPORT_ROUTE ], { useHash: true })
    ],
    declarations: [
      CreateReportComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MimsAppCreateReportModule {}
