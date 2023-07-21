package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.*;

public class PartSeedDTO {
    @Expose

    private String partName;
    @Expose

    private Double price;

    @Expose
    private String quantity;

    public PartSeedDTO() {
    }

    @NotNull
    @Size(min = 2, max = 19)
    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    @NotNull
    @DecimalMin(value = "10.0")
    @DecimalMax(value = "2000.0")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @NotNull
    @Positive
    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}