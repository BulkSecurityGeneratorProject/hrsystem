import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrsystemSharedModule } from '../../shared';
import { HrsystemAdminModule } from '../../admin/admin.module';
import {
    TicketService,
    TicketPopupService,
    TicketComponent,
    TicketDetailComponent,
    TicketDialogComponent,
    TicketPopupComponent,
    TicketDeletePopupComponent,
    TicketDeleteDialogComponent,
    TickethritPopupComponent,
    ticketRoute,
    ticketPopupRoute,
} from './';

const ENTITY_STATES = [
    ...ticketRoute,
    ...ticketPopupRoute,
];

@NgModule({
    imports: [
        HrsystemSharedModule,
        HrsystemAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TicketComponent,
        TicketDetailComponent,
        TicketDialogComponent,
        TicketDeleteDialogComponent,
        TicketPopupComponent,
        TicketDeletePopupComponent,
        TickethritPopupComponent,

    ],
    entryComponents: [
        TicketComponent,
        TicketDialogComponent,
        TicketPopupComponent,
        TicketDeleteDialogComponent,
        TicketDeletePopupComponent,
        TickethritPopupComponent
    ],
    providers: [
        TicketService,
        TicketPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HrsystemTicketModule {}
