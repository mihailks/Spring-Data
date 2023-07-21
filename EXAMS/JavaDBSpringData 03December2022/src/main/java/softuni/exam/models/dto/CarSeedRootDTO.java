package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarSeedRootDTO {

    @XmlElement(name = "car")
    List<CarSeedDTO> cars;

    public CarSeedRootDTO() {
    }

    public List<CarSeedDTO> getCars() {
        return cars;
    }

    public void setCars(List<CarSeedDTO> cars) {
        this.cars = cars;
    }
}
