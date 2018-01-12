import { BaseEntity } from './../../shared';

export class Department implements BaseEntity {
    constructor(
        public id?: number,
        public depName?: string,
        public employees?: BaseEntity[],
    ) {
    }
}
