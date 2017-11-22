import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { MimsSharedCommonModule } from '../shared';

import { REPORTBUILDER_ROUTE, ReportBuilderComponent } from './';
import {Step1VictimInfoComponent} from "./components/step1-victim-info/step1-victim-info.component";
import {Step2VictimInfoComponent} from "./components/step2-victim-details/step2-victim-details.component";

@NgModule({
    imports: [
        MimsSharedCommonModule,
        // Required for 'template-driven' (ngModel) forms
        FormsModule,
        // Required for 'reactive' (formGroup) forms
        ReactiveFormsModule,
        RouterModule.forRoot([ REPORTBUILDER_ROUTE ], { useHash: true })
    ],
    declarations: [
        ReportBuilderComponent,
        Step1VictimInfoComponent,
        Step2VictimInfoComponent
    ],
    entryComponents: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ReportBuilderModule {}
