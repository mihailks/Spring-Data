package bg.softuni.exercisexmlprocessing.model.DTO;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class ProductSeedDTO {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;

    public ProductSeedDTO() {
    }

    public ProductSeedDTO(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    @Size(min = 3)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Positive
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
