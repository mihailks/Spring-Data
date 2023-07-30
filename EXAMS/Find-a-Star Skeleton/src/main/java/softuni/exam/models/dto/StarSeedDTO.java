package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class StarSeedDTO {
    @Expose
    private String description;
    @Expose
    private Double lightYears;
    @Expose
    private String name;
    @Expose
    private String starType;
    @Expose
    private Long constellation;

    public StarSeedDTO() {
    }

    @NotNull
    @Size(min = 6)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull
    @Positive
    public Double getLightYears() {
        return lightYears;
    }

    public void setLightYears(Double lightYears) {
        this.lightYears = lightYears;
    }

    @NotNull
    @Size(min = 2, max = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public String getStarType() {
        return starType;
    }

    public void setStarType(String starType) {
        this.starType = starType;
    }

    @NotNull
    public Long getConstellation() {
        return constellation;
    }

    public void setConstellation(Long constellation) {
        this.constellation = constellation;
    }
}
