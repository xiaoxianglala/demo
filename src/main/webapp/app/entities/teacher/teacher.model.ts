import { BaseEntity, User } from './../../shared';

export class Teacher implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public sex?: string,
        public course?: string,
        public birthDate?: any,
        public phone?: string,
        public user?: User,
    ) {
    }
}
