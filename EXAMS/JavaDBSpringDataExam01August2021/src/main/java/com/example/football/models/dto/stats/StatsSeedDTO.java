package com.example.football.models.dto.stats;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class StatsSeedDTO {
    @XmlElement(name = "passing")
    private Double passing;
    @XmlElement(name = "shooting")
    private Double shooting;
    @XmlElement(name = "endurance")
    private Double endurance;

    public StatsSeedDTO() {
    }

    @NotNull
    @Positive
    public Double getPassing() {
        return passing;
    }

    public void setPassing(Double passing) {
        this.passing = passing;
    }

    @NotNull
    @Positive
    public Double getShooting() {
        return shooting;
    }

    public void setShooting(Double shooting) {
        this.shooting = shooting;
    }

    @NotNull
    @Positive
    public Double getEndurance() {
        return endurance;
    }

    public void setEndurance(Double endurance) {
        this.endurance = endurance;
    }
}
