package softuni.exam.domain.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "pictures")
@XmlAccessorType(XmlAccessType.FIELD)
public class PicturesSeedRootDTO {

    @XmlElement(name = "picture")
    private List<PicturesSeedDTO> pictures;

    public PicturesSeedRootDTO() {
    }

    public List<PicturesSeedDTO> getPictures() {
        return pictures;
    }

    public void setPictures(List<PicturesSeedDTO> pictures) {
        this.pictures = pictures;
    }
}
