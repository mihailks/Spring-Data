package Entities.P06FootballBettingDatabase;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "positions")
public class Position{
    private String id;
    private String positionDescription;

    private Set<Player> players;

        public Position(){}


    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Column(name = "position_description")
    public String getPositionDescription() {
        return positionDescription;
    }

    public void setPositionDescription(String positionDescription) {
        this.positionDescription = positionDescription;
    }

    @OneToMany
    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }
}
