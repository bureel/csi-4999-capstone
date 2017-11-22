import {Component, EventEmitter, Input, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {ReportBuilderForm} from '../../report-builder.form';
import {FormControl, FormGroup, Validators} from '@angular/forms';

//ALWAYS DECLARE COMPONENT IN MODULE
@Component({
    selector: 'app-step-2-victim-details',
    templateUrl: './step2-victim-details.component.html',
    styleUrls: ['./step2-victim-details.component.css']
})
export class Step2VictimInfoComponent implements OnInit {
    @Input()
    public form: ReportBuilderForm;
    @Output()
    public submit: EventEmitter<any> = new EventEmitter();
    /**
     * This step uses form groups. The form binds the form group and then references each field
     * in the form group by name.
     */
    public formGroup: FormGroup = new FormGroup({
        lastKnownLocation: new FormControl('Location'),
        lastSeen: new FormControl('Who were they with?'),
        serviceProvider: new FormControl('Verizon'),
        serviceProviderAccountNumber: new FormControl('(248)555-1234'),
        additionalInformation: new FormControl('ex. birthmarks, scars, etc.'),
    });
    constructor() { }

    ngOnInit() {
    }

    onSubmit() {
        if (this.formGroup.valid) {
            // Since the values in the inputs are bound to the form groups we need to copy them into the model.
            this.form.lastKnownLocation = this.formGroup.controls['lastKnownLocation'].value;
            this.form.lastSeen = this.formGroup.controls['lastSeen'].value;
            this.form.serviceProvider = this.formGroup.controls['serviceProvider'].value;
            this.form.serviceProviderAccountNumber = this.formGroup.controls['serviceProviderAccountNumber'].value;
            this.form.additionalInformation = this.formGroup.controls['additionalInformation'].value;
            this.submit.emit();
        }
    }
}