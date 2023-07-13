package bg.softuni.exercisejsonprocessing.model.DTO;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.Size;

public class CategorySeedDTO {
    @Expose
    private String name;

    public CategorySeedDTO() {
    }

    @Size(min = 3, max = 15)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
