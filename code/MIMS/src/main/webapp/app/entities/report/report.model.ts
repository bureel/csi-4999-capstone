import { BaseEntity } from './../../shared';

export class Report implements BaseEntity {
    constructor(
        public id?: number,
        public status?: string,
        public resolution?: string,
        public victimName?: string,
        public victimPhoneNumber?: string,
        public victimEmail?: string,
        public parentName?: string,
        public parentPhoneNumber?: string,
        public parentEmail?: string,
        public dateOfBirth?: any,
        public height?: string,
        public weight?: number,
        public eyeColor?: string,
        public demographic?: string,
        public lastKnownLocation?: string,
        public lastSeen?: any,
        public serviceProvider?: string,
        public serviceProviderAccountNumber?: string,
        public complaintNumber?: string,
        public reportNumber?: string,
        public investigatorName?: string,
        public investigatorEmail?: string,
        public createdAt?: any,
        public updatedAt?: any,
        public photosContentType?: string,
        public photos?: any,
        public additionalInformation?: string,
        public userId?: number,
    ) {
    }
}
