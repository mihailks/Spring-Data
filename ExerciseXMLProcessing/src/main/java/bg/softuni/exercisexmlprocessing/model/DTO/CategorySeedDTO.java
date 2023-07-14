package bg.softuni.exercisexmlprocessing.model.DTO;

import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategorySeedDTO {
    //    @XmlElement(name = "name")
    private String name;

    @Size(min = 3, max = 15)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
