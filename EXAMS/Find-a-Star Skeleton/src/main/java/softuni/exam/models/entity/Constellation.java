package softuni.exam.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "constellations")
public class Constellation extends BaseEntity {
    private String name;
    private String description;
    private List<Star> star;

    public Constellation() {
    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy = "constellation")
    public List<Star> getStar() {
        return star;
    }

    public void setStar(List<Star> star) {
        this.star = star;
    }
}
