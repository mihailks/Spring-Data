package Entities.P06FootballBettingDatabase.CompositeKeys;

import Entities.P06FootballBettingDatabase.Game;
import Entities.P06FootballBettingDatabase.Player;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;

import java.io.Serializable;

@Embeddable
public class PlayerStatisticsEmbKey implements Serializable{

        private Game game;
        private Player player;

        public PlayerStatisticsEmbKey(Game game, Player player) {
            this.game = game;
            this.player = player;
        }

        public PlayerStatisticsEmbKey() {

        }

        public Game getGame() {
            return game;
        }

        public void setGame(Game game) {
            this.game = game;
        }

        public Player getPlayer() {
            return player;
        }

        public void setPlayer(Player player) {
            this.player = player;
        }
}
