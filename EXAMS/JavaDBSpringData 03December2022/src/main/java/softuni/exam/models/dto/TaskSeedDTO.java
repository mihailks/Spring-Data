package softuni.exam.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class TaskSeedDTO {
    @NotNull
    @Positive
    @XmlElement(name = "price")
    private BigDecimal price;
    @NotNull
    @XmlElement(name = "date")
    private String date;
    @NotNull
    @XmlElement(name = "mechanic")
    private MechanicFirstNameDTO mechanic;
    @NotNull
    @XmlElement(name = "part")
    private PartIdDTO part;
    @NotNull
    @XmlElement(name = "car")
    private CarIdDTO car;

    public TaskSeedDTO() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public MechanicFirstNameDTO getMechanic() {
        return mechanic;
    }

    public void setMechanic(MechanicFirstNameDTO mechanic) {
        this.mechanic = mechanic;
    }

    public PartIdDTO getPart() {
        return part;
    }

    public void setPart(PartIdDTO part) {
        this.part = part;
    }

    public CarIdDTO getCar() {
        return car;
    }

    public void setCar(CarIdDTO car) {
        this.car = car;
    }
}
