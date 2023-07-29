package softuni.exam.models.dto.ticket;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "tickets")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketSeedRootDTO {
    @XmlElement(name = "ticket")
    private List<TicketSeedDTO>tickets;

    public TicketSeedRootDTO() {
    }

    public List<TicketSeedDTO> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketSeedDTO> tickets) {
        this.tickets = tickets;
    }
}
