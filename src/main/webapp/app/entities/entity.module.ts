import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { DemoStudentModule } from './student/student.module';
import { DemoTeacherModule } from './teacher/teacher.module';
import { DemoCourseModule } from './course/course.module';
import { DemoScoreModule } from './score/score.module';
import { DemoDepartmentModule } from './department/department.module';
import { DemoEmployeeModule } from './employee/employee.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        DemoStudentModule,
        DemoTeacherModule,
        DemoCourseModule,
        DemoScoreModule,
        DemoDepartmentModule,
        DemoEmployeeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DemoEntityModule {}
