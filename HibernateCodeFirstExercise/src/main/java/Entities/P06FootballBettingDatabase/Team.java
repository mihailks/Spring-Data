package Entities.P06FootballBettingDatabase;

import Entities.BaseEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "teams")
public class Team extends BaseEntity {
    private String name;
    private String logo;
    private String Initials;
    private String primaryKitColor;
    private String secondaryKitColor;
    private String town;
    private BigDecimal budget;

    public Team() {
    }

    @Column(unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(unique = true)
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Column(unique = true)
    public String getInitials() {
        return Initials;
    }

    public void setInitials(String initials) {
        Initials = initials;
    }

    @Column(name = "primary_kit_color")
    public String getPrimaryKitColor() {
        return primaryKitColor;
    }

    public void setPrimaryKitColor(String primaryKitColor) {
        this.primaryKitColor = primaryKitColor;
    }

    @Column(name = "secondary_kit_color")
    public String getSecondaryKitColor() {
        return secondaryKitColor;
    }

    public void setSecondaryKitColor(String secondaryKitColor) {
        this.secondaryKitColor = secondaryKitColor;
    }

    @Basic
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Basic
    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }
}
