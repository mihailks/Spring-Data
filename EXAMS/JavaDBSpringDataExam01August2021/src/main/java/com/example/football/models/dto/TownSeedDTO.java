package com.example.football.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class TownSeedDTO {
    @Expose
    private String name;
    @Expose
    private Integer population;
    @Expose
    private String travelGuide;

    public TownSeedDTO() {
    }
    @NotNull
    @Size(min = 2)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @NotNull
    @Positive
    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }
    @NotNull
    @Size(min = 10)
    public String getTravelGuide() {
        return travelGuide;
    }

    public void setTravelGuide(String travelGuide) {
        this.travelGuide = travelGuide;
    }
}
