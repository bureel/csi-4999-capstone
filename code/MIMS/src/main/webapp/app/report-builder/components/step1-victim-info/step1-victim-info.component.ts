import {Component, EventEmitter, Input, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {ReportBuilderForm} from '../../report-builder.form';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
    selector: 'app-step-1-victim-info',
    templateUrl: './step1-victim-info.component.html',
    styleUrls: ['./step1-victim-info.component.css']
})
export class Step1VictimInfoComponent implements OnInit {
    @Input()
    public form: ReportBuilderForm;
    @Output()
    public submit: EventEmitter<any> = new EventEmitter();
    /**
     * This step uses form groups. The form binds the form group and then references each field
     * in the form group by name.
     */
    public formGroup: FormGroup = new FormGroup({
        victimName: new FormControl('Default name', [Validators.required]),
        victimPhoneNumber: new FormControl(),
        victimEmail: new FormControl(),
        dateOfBirth: new FormControl(),
        height: new FormControl(),
        weight: new FormControl(),
        eyeColor: new FormControl(),
        demographic: new FormControl(),

    });
    constructor() { }

    ngOnInit() {
    }

    onSubmit() {
        if (this.formGroup.valid) {
            // Since the values in the inputs are bound to the form groups we need to copy them into the model.
            this.form.victimName = this.formGroup.controls['victimName'].value;
            this.form.victimPhoneNumber = this.formGroup.controls['victimPhoneNumber'].value;
            this.form.victimEmail = this.formGroup.controls['victimEmail'].value;
            this.form.dateOfBirth = this.formGroup.controls['dateOfBirth'].value;
            this.form.height = this.formGroup.controls['height'].value;
            this.form.weight = this.formGroup.controls['weight'].value;
            this.form.eyeColor = this.formGroup.controls['eyeColor'].value;
            this.form.demographic = this.formGroup.controls['demographic'].value;
            this.submit.emit();
        }
    }
}
