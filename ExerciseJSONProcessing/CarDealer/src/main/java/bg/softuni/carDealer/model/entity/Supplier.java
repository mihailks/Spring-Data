package bg.softuni.carDealer.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "supplier")
public class Supplier extends BaseEntity {

    private String name;
    private Boolean isImporter;
    private List<Part> parts;

    public Supplier() {
    }

    public Supplier(String name, Boolean isImporter, List<Part> parts) {
        this.name = name;
        this.isImporter = isImporter;
        this.parts = parts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "is_importer")
    public Boolean getIsImporter() {
        return isImporter;
    }

    public void setIsImporter(Boolean imported) {
        isImporter = imported;
    }

    @OneToMany
    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }
}
