package Entities.P06FootballBettingDatabase;

import Entities.BaseEntity;
import Entities.P06FootballBettingDatabase.Enums.CompetitionLocation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "competition_types")
public class CompetitionTypes extends BaseEntity {
    private CompetitionLocation location;


    public CompetitionTypes() {
    }
    @Column(name = "location")
    public CompetitionLocation getLocation() {
        return location;
    }

    public void setLocation(CompetitionLocation location) {
        this.location = location;
    }
}
