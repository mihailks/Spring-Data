package bg.softuni.carDealer.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "supplier")
public class Supplier extends BaseEntity {

    private String name;
    private Boolean isImporter;


    public Supplier() {
    }

    public Supplier(String name, Boolean isImporter) {
        this.name = name;
        this.isImporter = isImporter;
    }

    @Column
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

}
