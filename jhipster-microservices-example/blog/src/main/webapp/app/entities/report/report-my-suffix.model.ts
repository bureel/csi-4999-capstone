import { BaseEntity } from './../../shared';

export class ReportMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public victimName?: string,
        public parentName?: string,
        public parentPhoneNumber?: string,
        public dateOfBirth?: any,
        public phoneNumber?: string,
        public height?: string,
        public weight?: number,
        public eyeColor?: string,
        public demographic?: string,
        public lastKnownLocation?: string,
        public timeOfLastSeen?: any,
        public serviceProvider?: string,
        public masterAccountNumber?: string,
        public complaintNumber?: string,
        public reportNumber?: string,
        public investigatorEmail?: string,
    ) {
    }
}
