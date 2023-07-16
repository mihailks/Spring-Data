package bg.softuni.carDealer.model.DTO.O1seedDTO;

import com.google.gson.annotations.Expose;

public class SupplierSeedDTO {
    @Expose
    private String name;
    @Expose
    private Boolean isImported;

    public SupplierSeedDTO() {
    }

    public SupplierSeedDTO(String name, Boolean isImported) {
        this.name = name;
        this.isImported = isImported;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getImported() {
        return isImported;
    }

    public void setImported(Boolean imported) {
        isImported = imported;
    }
}
