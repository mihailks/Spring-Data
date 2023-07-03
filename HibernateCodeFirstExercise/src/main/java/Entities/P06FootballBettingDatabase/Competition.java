package Entities.P06FootballBettingDatabase;

import Entities.BaseEntity;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "competition")
public class Competition extends BaseEntity {
    private String name;
    private CompetitionTypes type;
    private Set<Game> games;

    public Competition() {
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @ManyToOne
    public CompetitionTypes getType() {
        return type;
    }

    public void setType(CompetitionTypes type) {
        this.type = type;
    }

    @OneToMany(mappedBy = "competition")
    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public void setName(String name) {
        this.name = name;

    }
}
