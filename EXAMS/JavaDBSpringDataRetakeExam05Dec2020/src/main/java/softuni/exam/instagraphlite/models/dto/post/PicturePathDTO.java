package softuni.exam.instagraphlite.models.dto.post;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PicturePathDTO {
    @XmlElement(name = "path")
    private String path;

    public PicturePathDTO() {
    }
@NotNull
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
