import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MimsReportMySuffixModule } from './report/report-my-suffix.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        MimsReportMySuffixModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MimsEntityModule {}
