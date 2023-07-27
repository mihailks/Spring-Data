package com.example.football.models.dto.player;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "players")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayersSeedRootDTO {

    @XmlElement(name = "player")
    private List<PlayerSeedDTO> players;

    public PlayersSeedRootDTO() {
    }

    public List<PlayerSeedDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerSeedDTO> players) {
        this.players = players;
    }
}
