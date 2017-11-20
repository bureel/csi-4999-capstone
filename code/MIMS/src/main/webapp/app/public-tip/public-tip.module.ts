import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MimsSharedCommonModule } from '../shared';

import { PUBLIC_TIP_ROUTE, PublicTipComponent } from './';

@NgModule({
    imports: [
      MimsSharedCommonModule,
      RouterModule.forRoot([ PUBLIC_TIP_ROUTE ], { useHash: true })
    ],
    declarations: [
      PublicTipComponent,
    ],
    entryComponents: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MimsAppPublicTipModule {}
