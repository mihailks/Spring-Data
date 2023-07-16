package bg.softuni.exercisexmlprocessing.model.DTO.Q2;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersViewRootDTO {
    @XmlElement(name = "user")
    private List<UserWithProductsDTO> products;


    public List<UserWithProductsDTO> getProducts() {
        return products;
    }

    public void setProducts(List<UserWithProductsDTO> products) {
        this.products = products;
    }
}
