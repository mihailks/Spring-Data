package bg.softuni.exercisexmlprocessing.model.DTO;

import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.*;

import java.math.BigDecimal;

@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductSeedDTO {

    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "price")
    private BigDecimal price;

    @Size(min = 3, max = 15)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
