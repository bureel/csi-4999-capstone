/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MimsTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ReportMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/report/report-my-suffix-detail.component';
import { ReportMySuffixService } from '../../../../../../main/webapp/app/entities/report/report-my-suffix.service';
import { ReportMySuffix } from '../../../../../../main/webapp/app/entities/report/report-my-suffix.model';

describe('Component Tests', () => {

    describe('ReportMySuffix Management Detail Component', () => {
        let comp: ReportMySuffixDetailComponent;
        let fixture: ComponentFixture<ReportMySuffixDetailComponent>;
        let service: ReportMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MimsTestModule],
                declarations: [ReportMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ReportMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(ReportMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReportMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReportMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ReportMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.report).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
