import { BaseEntity } from './../../shared';

export class Tip implements BaseEntity {
    constructor(
        public id?: number,
        public information?: string,
        public photoContentType?: string,
        public photo?: any,
        public email?: string,
        public report?: BaseEntity,
    ) {
    }
}
