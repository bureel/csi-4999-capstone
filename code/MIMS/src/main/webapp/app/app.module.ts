import './vendor.ts';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { MimsSharedModule, UserRouteAccessService } from './shared';
import { MimsHomeModule } from './home/home.module';
import { MimsAdminModule } from './admin/admin.module';
import { MimsAccountModule } from './account/account.module';
import { MimsEntityModule } from './entities/entity.module';

import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

import { MimsAppCreateReportModule } from './create-report/create-report.module';
import { MimsAppReportPageModule } from './report-page/report-page.module';
import { MimsAppViewVictimsModule } from './view-victims/view-victims.module';
import { MimsAppSingleDetailModule } from './single-detail/single-detail.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        MimsSharedModule,
        MimsHomeModule,
        MimsAdminModule,
        MimsAccountModule,
        MimsEntityModule,
        MimsAppCreateReportModule,
        MimsAppReportPageModule,
        MimsAppViewVictimsModule,
        MimsAppSingleDetailModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class MimsAppModule {}
