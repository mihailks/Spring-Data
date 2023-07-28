package softuni.exam.instagraphlite.models.dto.post;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PostSeedDTO {
    @XmlElement(name = "caption")
    private String caption;
    @XmlElement(name = "user")
    private UserUsernameDTO user;
    @XmlElement(name = "picture")
    private PicturePathDTO picture;

    public PostSeedDTO() {
    }

    @NotNull
    @Size(min = 21)
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
    @NotNull
    public UserUsernameDTO getUser() {
        return user;
    }

    public void setUser(UserUsernameDTO user) {
        this.user = user;
    }
    @NotNull
    public PicturePathDTO getPicture() {
        return picture;
    }

    public void setPicture(PicturePathDTO picture) {
        this.picture = picture;
    }
}
