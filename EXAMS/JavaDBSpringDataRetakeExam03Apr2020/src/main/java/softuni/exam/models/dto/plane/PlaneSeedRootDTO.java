package softuni.exam.models.dto.plane;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "planes")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlaneSeedRootDTO {

    @XmlElement(name = "plane")
    private List<PlaneSeedDTO> planes;

    public PlaneSeedRootDTO() {
    }


    public List<PlaneSeedDTO> getPlanes() {
        return planes;
    }

    public void setPlanes(List<PlaneSeedDTO> planes) {
        this.planes = planes;
    }
}
