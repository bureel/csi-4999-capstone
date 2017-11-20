/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MimsTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TipDetailComponent } from '../../../../../../main/webapp/app/entities/tip/tip-detail.component';
import { TipService } from '../../../../../../main/webapp/app/entities/tip/tip.service';
import { Tip } from '../../../../../../main/webapp/app/entities/tip/tip.model';

describe('Component Tests', () => {

    describe('Tip Management Detail Component', () => {
        let comp: TipDetailComponent;
        let fixture: ComponentFixture<TipDetailComponent>;
        let service: TipService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MimsTestModule],
                declarations: [TipDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TipService,
                    JhiEventManager
                ]
            }).overrideTemplate(TipDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TipDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Tip(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.tip).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
