package bg.softuni.exercisexmlprocessing.model.DTO;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoriesSeedRootDTO{

    @XmlElement(name = "category")
    private List<CategorySeedDTO> categories;

    public List<CategorySeedDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategorySeedDTO> categories) {
        this.categories = categories;
    }
}

