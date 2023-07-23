package softuni.exam.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity {
    private String url;

    public Picture() {
    }

    @Column(nullable = false)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
