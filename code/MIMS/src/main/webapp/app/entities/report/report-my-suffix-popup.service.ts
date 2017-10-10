import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { ReportMySuffix } from './report-my-suffix.model';
import { ReportMySuffixService } from './report-my-suffix.service';

@Injectable()
export class ReportMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private reportService: ReportMySuffixService

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
                    report.dateOfBirth = this.datePipe
                        .transform(report.dateOfBirth, 'yyyy-MM-ddTHH:mm:ss');
                    report.timeOfLastSeen = this.datePipe
                        .transform(report.timeOfLastSeen, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.reportModalRef(component, report);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.reportModalRef(component, new ReportMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    reportModalRef(component: Component, report: ReportMySuffix): NgbModalRef {
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
