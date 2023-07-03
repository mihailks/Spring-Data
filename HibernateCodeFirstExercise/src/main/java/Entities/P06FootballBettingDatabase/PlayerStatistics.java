package Entities.P06FootballBettingDatabase;

import Entities.P06FootballBettingDatabase.CompositeKeys.PlayerStatisticsEmbKey;
import jakarta.persistence.*;

@Entity
@IdClass(PlayerStatisticsEmbKey.class)
@Table(name = "player_statistics")
public class PlayerStatistics {
    private Game game;
    private Player player;
    private int ScoredGoals;
    private int PlayerAssists;
    private int PlayedMinutesDuringGame;

    public PlayerStatistics() {
    }

    @Id
    @JoinColumn(name = "game_id",referencedColumnName = "id")
    @ManyToOne
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Id
    @JoinColumn(name = "player_id",referencedColumnName = "id")
    @ManyToOne
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Column(name = "scoder_goals")
    public int getScoredGoals() {
        return ScoredGoals;
    }

    public void setScoredGoals(int scoredGoals) {
        ScoredGoals = scoredGoals;
    }

    @Column(name = "player_assists")
    public int getPlayerAssists() {
        return PlayerAssists;
    }

    public void setPlayerAssists(int playerAssists) {
        PlayerAssists = playerAssists;
    }

    @Column(name = "player_minutes_during_game")
    public int getPlayedMinutesDuringGame() {
        return PlayedMinutesDuringGame;
    }

    public void setPlayedMinutesDuringGame(int playedMinutesDuringGam) {
        PlayedMinutesDuringGame = playedMinutesDuringGam;
    }


}
