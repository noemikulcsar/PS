package com.example.demo.export;
import com.example.demo.dto.ticket.TicketDetailsDTO;
import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "tickets")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketListXmlWrapper {

    @XmlElement(name = "ticket")
    private List<TicketDetailsDTO> tickets;

    public TicketListXmlWrapper() {}

    public TicketListXmlWrapper(List<TicketDetailsDTO> tickets) {
        this.tickets = tickets;
    }
    public List<TicketDetailsDTO> getTickets() {
        return tickets;
    }
    public void setTickets(List<TicketDetailsDTO> tickets) {
        this.tickets = tickets;
    }
}

