package hiberspring.domain.dtos;

import com.google.gson.annotations.Expose;
import hiberspring.domain.entities.Town;

import javax.validation.constraints.NotNull;

public class BranchSeedDTO {
    @Expose
    @NotNull
    private String name;
    @Expose
    @NotNull
    private String town;

    public BranchSeedDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
