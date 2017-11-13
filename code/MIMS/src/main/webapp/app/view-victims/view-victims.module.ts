import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MimsSharedCommonModule } from '../shared';

import { VIEW_VICTIMS_ROUTE, ViewVictimsComponent, ReportPageResolvePagingParams } from './';

@NgModule({
    imports: [
      MimsSharedCommonModule,
      RouterModule.forRoot([ VIEW_VICTIMS_ROUTE ], { useHash: true })
    ],
    declarations: [
      ViewVictimsComponent,
    ],
    entryComponents: [
    ],
    providers: [
      ReportPageResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MimsAppViewVictimsModule {}
