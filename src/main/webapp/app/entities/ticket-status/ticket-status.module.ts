import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrsystemSharedModule } from '../../shared';
import {
    TicketStatusService,
    TicketStatusPopupService,
    TicketStatusComponent,
    TicketStatusDetailComponent,
    TicketStatusDialogComponent,
    TicketStatusPopupComponent,
    TicketStatusDeletePopupComponent,
    TicketStatusDeleteDialogComponent,
    ticketStatusRoute,
    ticketStatusPopupRoute,
} from './';

const ENTITY_STATES = [
    ...ticketStatusRoute,
    ...ticketStatusPopupRoute,
];

@NgModule({
    imports: [
        HrsystemSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TicketStatusComponent,
        TicketStatusDetailComponent,
        TicketStatusDialogComponent,
        TicketStatusDeleteDialogComponent,
        TicketStatusPopupComponent,
        TicketStatusDeletePopupComponent,
    ],
    entryComponents: [
        TicketStatusComponent,
        TicketStatusDialogComponent,
        TicketStatusPopupComponent,
        TicketStatusDeleteDialogComponent,
        TicketStatusDeletePopupComponent,
    ],
    providers: [
        TicketStatusService,
        TicketStatusPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HrsystemTicketStatusModule {}
