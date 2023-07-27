package com.example.football.models.dto.player;


import com.example.football.models.entity.Position;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerSeedDTO {

    @XmlElement(name = "first-name")
    private String firstName;
    @XmlElement(name = "last-name")
    private String lastName;
    @XmlElement(name = "email")
    private String email;
    @XmlElement(name = "birth-date")
    private String birthDate;
    @XmlElement(name = "position")
    private Position position;
    @XmlElement(name = "town")
    private TownNameDTO town;
    @XmlElement(name = "team")
    private TeamNameDTO team;
    @XmlElement(name = "stat")
    private StatsIdDTO stat;

    public PlayerSeedDTO() {
    }

    @NotNull
    @Size(min = 2)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotNull
    @Size(min = 2)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotNull
    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull
    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @NotNull
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @NotNull
    public TownNameDTO getTown() {
        return town;
    }

    public void setTown(TownNameDTO town) {
        this.town = town;
    }

    @NotNull
    public TeamNameDTO getTeam() {
        return team;
    }

    public void setTeam(TeamNameDTO team) {
        this.team = team;
    }

    @NotNull
    public StatsIdDTO getStat() {
        return stat;
    }

    public void setStat(StatsIdDTO stat) {
        this.stat = stat;
    }
}
