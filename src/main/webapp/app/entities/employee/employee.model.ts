import { BaseEntity } from './../../shared';

export class Employee implements BaseEntity {
    constructor(
        public id?: number,
        public empName?: string,
        public department?: BaseEntity,
    ) {
    }
}
