package bg.softuni.exercisexmlprocessing.model.DTO;


import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductSeedRootDTO {

    @XmlElement(name = "product")
    private List<ProductSeedDTO> products;

    public List<ProductSeedDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductSeedDTO> products) {
        this.products = products;
    }
}
