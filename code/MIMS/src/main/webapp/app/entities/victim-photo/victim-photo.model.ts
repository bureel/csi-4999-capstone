import { BaseEntity } from './../../shared';

export class VictimPhoto implements BaseEntity {
    constructor(
        public id?: number,
        public photoContentType?: string,
        public photo?: any,
        public report?: BaseEntity,
    ) {
    }
}
