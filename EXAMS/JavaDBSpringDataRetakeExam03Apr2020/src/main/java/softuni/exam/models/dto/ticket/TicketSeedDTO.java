package softuni.exam.models.dto.ticket;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class TicketSeedDTO {
    @XmlElement(name = "serial-number")
    private String serialNumber;
    @XmlElement(name = "price")
    private BigDecimal price;
    @XmlElement(name = "take-off")
    private String takeOff;
    @XmlElement(name = "from-town")
    private TownNameDTO fromTown;
    @XmlElement(name = "to-town")
    private TownNameDTO toTown;
    @XmlElement(name = "passenger")
    private PassengerEmailDTO passenger;
    @XmlElement(name = "plane")
    private PlaneRegisterNumberDTO plane;

    public TicketSeedDTO() {
    }

    @NotNull
    @Size(min = 2)
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @NotNull
    @Positive
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @NotNull
    public String getTakeOff() {
        return takeOff;
    }

    public void setTakeOff(String takeOff) {
        this.takeOff = takeOff;
    }

    @NotNull
    public TownNameDTO getFromTown() {
        return fromTown;
    }

    public void setFromTown(TownNameDTO fromTown) {
        this.fromTown = fromTown;
    }

    @NotNull
    public TownNameDTO getToTown() {
        return toTown;
    }

    public void setToTown(TownNameDTO toTown) {
        this.toTown = toTown;
    }

    @NotNull
    public PassengerEmailDTO getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerEmailDTO passenger) {
        this.passenger = passenger;
    }

    @NotNull
    public PlaneRegisterNumberDTO getPlane() {
        return plane;
    }

    public void setPlane(PlaneRegisterNumberDTO plane) {
        this.plane = plane;
    }
}
