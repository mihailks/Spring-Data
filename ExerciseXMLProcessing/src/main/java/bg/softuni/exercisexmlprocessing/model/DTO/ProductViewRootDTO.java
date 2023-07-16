package bg.softuni.exercisexmlprocessing.model.DTO;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductViewRootDTO {
    @XmlElement(name = "product")
    private List<ProductWithSellerDTO> products;

    public List<ProductWithSellerDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductWithSellerDTO> products) {
        this.products = products;
    }
}
