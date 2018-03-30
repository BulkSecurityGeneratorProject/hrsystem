import {Component, OnInit} from '@angular/core';
import {Ticket} from '../entities/ticket/ticket.model';
import {Subscription} from 'rxjs/Subscription';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';
import {Principal} from '../shared';
import {TicketService} from '../entities/ticket/ticket.service';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';

@Component({
    selector: 'jhi-ticket-available',
    templateUrl: './ticket-available.component.html',
    styles: []
})
export class TicketAvailableComponent implements OnInit {

    HRtickets: Ticket[];
    ITtickets: Ticket[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private ticketService: TicketService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    // loadAll() {
    //     this.ticketService.query().subscribe(
    //         (res: HttpResponse<Ticket[]>) => {
    //             this.tickets = res.body;
    //         },
    //         (res: HttpErrorResponse) => this.onError(res.message)
    //     );
    // }

    loadHRTickets() {
        this.ticketService.HRTicketquery().subscribe(
            (res: HttpResponse<Ticket[]>) => {
                this.HRtickets = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    loadITTickets() {
        this.ticketService.ITTicketquery().subscribe(
            (res: HttpResponse<Ticket[]>) => {
                this.ITtickets = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadHRTickets();
        this.loadITTickets();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTickets();
    }


    trackId(index: number, item: Ticket) {
        return item.id;
    }
    registerChangeInTickets() {
        this.eventSubscriber = this.eventManager.subscribe('ticketListModification', (response) => this.loadHRTickets());
        this.eventSubscriber = this.eventManager.subscribe('ticketListModification', (response) => this.loadITTickets());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
