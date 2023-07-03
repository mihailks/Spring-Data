package Entities.P06FootballBettingDatabase.CompositeKeys;

import Entities.P06FootballBettingDatabase.Bet;
import Entities.P06FootballBettingDatabase.Game;

import java.io.Serializable;

public class BetGameEmpKey implements Serializable {
    private Game game;
    private Bet bet;

    public BetGameEmpKey(Game game, Bet bet) {
        this.game = game;
        this.bet = bet;
    }

    public BetGameEmpKey() {
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Bet getBet() {
        return bet;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
    }
}
