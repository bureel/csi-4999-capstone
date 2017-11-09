/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MimsTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { VictimPhotoDetailComponent } from '../../../../../../main/webapp/app/entities/victim-photo/victim-photo-detail.component';
import { VictimPhotoService } from '../../../../../../main/webapp/app/entities/victim-photo/victim-photo.service';
import { VictimPhoto } from '../../../../../../main/webapp/app/entities/victim-photo/victim-photo.model';

describe('Component Tests', () => {

    describe('VictimPhoto Management Detail Component', () => {
        let comp: VictimPhotoDetailComponent;
        let fixture: ComponentFixture<VictimPhotoDetailComponent>;
        let service: VictimPhotoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MimsTestModule],
                declarations: [VictimPhotoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    VictimPhotoService,
                    JhiEventManager
                ]
            }).overrideTemplate(VictimPhotoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VictimPhotoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VictimPhotoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new VictimPhoto(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.victimPhoto).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
