package com.hrsystem.repository;

import com.hrsystem.domain.Ticket;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Ticket entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("select ticket from Ticket ticket where ticket.user.login = ?#{principal.username}")
    List<Ticket> findByUserIsCurrentUser();

    @Query("select ticket from Ticket ticket where ticket.ticketRequst.department ='HR'")
    List<Ticket>findHrTickets();

}
