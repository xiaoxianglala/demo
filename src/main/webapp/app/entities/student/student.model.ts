import { BaseEntity, User } from './../../shared';

export class Student implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public age?: number,
        public phone?: string,
        public sex?: string,
        public birthDate?: any,
        public user?: User,
    ) {
    }
}
