package softuni.exam.models.dto.Offer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class OfferSeedDTO {
    @XmlElement(name = "price")
    private BigDecimal price;
    @XmlElement(name = "agent")
    private AgentNameDTO agentNameDTO;
    @XmlElement(name = "apartment")
    private ApartmentIdDTO apartmentIdDTO;
    @XmlElement(name = "publishedOn")
    private String publishedOn;

    public OfferSeedDTO() {
    }

    @Positive
    @NotNull
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @NotNull
    public AgentNameDTO getAgentNameDTO() {
        return agentNameDTO;
    }

    public void setAgentNameDTO(AgentNameDTO agentNameDTO) {
        this.agentNameDTO = agentNameDTO;
    }

    @NotNull
    public ApartmentIdDTO getApartmentIdDTO() {
        return apartmentIdDTO;
    }

    public void setApartmentIdDTO(ApartmentIdDTO apartmentIdDTO) {
        this.apartmentIdDTO = apartmentIdDTO;
    }

    @NotNull
    public String getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(String publishedOn) {
        this.publishedOn = publishedOn;
    }
}
