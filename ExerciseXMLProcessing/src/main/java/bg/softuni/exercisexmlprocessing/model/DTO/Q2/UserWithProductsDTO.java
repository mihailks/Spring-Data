package bg.softuni.exercisexmlprocessing.model.DTO.Q2;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserWithProductsDTO {

    @XmlAttribute(name = "first-name")
        private String firstName;
    @XmlAttribute(name = "last-name")
        private String lastName;

    @XmlElement(name = "product")
    @XmlElementWrapper(name = "sold-products")
    private List<ProductWithBuyerDTO> products;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<ProductWithBuyerDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductWithBuyerDTO> products) {
        this.products = products;
    }
}
