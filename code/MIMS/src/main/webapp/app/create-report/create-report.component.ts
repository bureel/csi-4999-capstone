import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Report } from '../entities/report/report.model'
import { ReportService } from '../entities/report/report.service';
import { VictimPhoto } from '../entities/victim-photo/victim-photo.model'
import { VictimPhotoService } from '../entities/victim-photo/victim-photo.service'

@Component({
  selector: 'jhi-create-report',
  templateUrl: './create-report.component.html',
  styleUrls: [
    'create-report.css'
  ]
})
export class CreateReportComponent implements OnInit {

  message: string;
  step: number;
  report: Report;
  savedReport: Report;
  victimPhotos: VictimPhoto[];
  isSaving: boolean;
  dateOfBirthDp: any;
  checkedIndex: number;

  constructor(
      private dataUtils: JhiDataUtils,
      private jhiAlertService: JhiAlertService,
      private reportService: ReportService,
      private victimPhotoService: VictimPhotoService,
      private eventManager: JhiEventManager,
      private router: Router
  ) {
  }

  ngOnInit() {
      this.report = new Report();
      this.victimPhotos = [];
      this.isSaving = false;
      this.checkedIndex = 1;
  }

  advance() {
      if (this.checkedIndex < 7) {
        this.checkedIndex++;
      }
  }

  back() {
    if (this.checkedIndex > 1) {
      this.checkedIndex--;
    }
}

  byteSize(field) {
      return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
      return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, entity, field, isImage) {
      this.dataUtils.setFileData(event, entity, field, isImage);
  }

  setPhotosData(fileInput) {
    for (let i = 0; i < fileInput.target.files.length; i++) {
        if (!this.victimPhotos[i]) {
            this.victimPhotos[i] = new VictimPhoto()
        }
        const entity = this.victimPhotos[i];
        this.dataUtils.toBase64(fileInput.target.files[i], function(base64Data) {
            entity['photo'] = base64Data;
            entity['photo' + 'ContentType'] = fileInput.target.files[i].type;
        });
    }
}

  save() {
      this.isSaving = true;
      if (this.report.id !== undefined) {
          this.subscribeToSaveResponse(
              this.reportService.update(this.report));
      } else {
          this.subscribeToSaveResponse(
              this.reportService.create(this.report));
      }
  }

  private subscribeToSaveResponse(result: Observable<Report>) {
      result.subscribe((res: Report) =>
          this.onSaveSuccess(res), (res: Response) => this.onSaveError());
  }

  private subscribeToPhotoSaveResponse(result: Observable<VictimPhoto>) {
    result.subscribe((res: VictimPhoto) =>
        this.onPhotoSaveSuccess(res), (res: Response) => this.onSaveError());
    }

  private onSaveSuccess(result: Report) {
      this.eventManager.broadcast({ name: 'reportListModification', content: 'OK'});
      console.log(result);
      for (let i = 0; i < this.victimPhotos.length; i++) {
        this.victimPhotos[i].report = result;
        if (this.victimPhotos[i].id !== undefined) {
          this.subscribeToPhotoSaveResponse(
              this.victimPhotoService.update(this.victimPhotos[i]));
      } else {
          this.subscribeToPhotoSaveResponse(
              this.victimPhotoService.create(this.victimPhotos[i]));
      }
  }
      this.isSaving = false;
  }

  private onPhotoSaveSuccess(result: VictimPhoto) {
    this.eventManager.broadcast({ name: 'victimPhotoListModification', content: 'OK'});
    this.isSaving = false;
    this.router.navigate(['/family-home']);
}

  private onSaveError() {
      this.isSaving = false;
  }

  private onError(error: any) {
      this.jhiAlertService.error(error.message, null, null);
  }
}
