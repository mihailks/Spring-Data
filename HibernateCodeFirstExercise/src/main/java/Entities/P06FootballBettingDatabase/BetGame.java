package Entities.P06FootballBettingDatabase;

import Entities.BaseEntity;
import Entities.P06FootballBettingDatabase.CompositeKeys.BetGameEmpKey;
import jakarta.persistence.*;

@Entity
@IdClass(BetGameEmpKey.class)
@Table(name = "bet_game")
public class BetGame extends BaseEntity {

    private Game game;
    private Bet bet;
    private ResultPrediction resultPrediction;

    public BetGame() {
    }

    @ManyToOne
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @OneToOne
    public Bet getBet() {
        return bet;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
    }

    @OneToOne
    public ResultPrediction getResultPrediction() {
        return resultPrediction;
    }

    public void setResultPrediction(ResultPrediction resultPrediction) {
        this.resultPrediction = resultPrediction;
    }


}
