import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MimsReportModule } from './report/report.module';
import { MimsVictimPhotoModule } from './victim-photo/victim-photo.module';
import { MimsTipModule } from './tip/tip.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        MimsReportModule,
        MimsVictimPhotoModule,
        MimsTipModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MimsEntityModule {}
