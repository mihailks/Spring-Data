package Entities.P06FootballBettingDatabase;

import Entities.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "bets")
public class Bet extends BaseEntity {
    private Double betMoney;
    private LocalDate temporalInfo;
    private User user;
    private Set<Game> games;

    public Bet() {
    }

    @ManyToMany
    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    @Column(name = "bet_money")
    public Double getBetMoney() {
        return betMoney;
    }

    public void setBetMoney(Double betMoney) {
        this.betMoney = betMoney;
    }

    public LocalDate getTemporalInfo() {
        return temporalInfo;
    }

    public void setTemporalInfo(LocalDate temporalInfo) {
        this.temporalInfo = temporalInfo;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
