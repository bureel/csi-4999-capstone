import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Report } from './report.model';
import { ReportService } from './report.service';

@Injectable()
export class ReportPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private reportService: ReportService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.reportService.find(id).subscribe((report) => {
                    if (report.dateOfBirth) {
                        report.dateOfBirth = {
                            year: report.dateOfBirth.getFullYear(),
                            month: report.dateOfBirth.getMonth() + 1,
                            day: report.dateOfBirth.getDate()
                        };
                    }
                    report.lastSeen = this.datePipe
                        .transform(report.lastSeen, 'yyyy-MM-ddTHH:mm:ss');
                    report.createdAt = this.datePipe
                        .transform(report.createdAt, 'yyyy-MM-ddTHH:mm:ss');
                    report.updatedAt = this.datePipe
                        .transform(report.updatedAt, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.reportModalRef(component, report);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.reportModalRef(component, new Report());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    reportModalRef(component: Component, report: Report): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.report = report;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
