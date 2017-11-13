import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MimsSharedCommonModule } from '../shared';

import { SINGLE_DETAIL_ROUTE, SingleDetailComponent } from './';

@NgModule({
    imports: [
      MimsSharedCommonModule,
      RouterModule.forRoot([ SINGLE_DETAIL_ROUTE ], { useHash: true })
    ],
    declarations: [
      SingleDetailComponent,
    ],
    entryComponents: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MimsAppSingleDetailModule {}
