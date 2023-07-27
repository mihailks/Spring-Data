package com.example.football.service.impl;

import com.example.football.models.dto.player.PlayersSeedRootDTO;
import com.example.football.models.entity.Player;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import com.example.football.util.ValidationUtil;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {
    public static final String PLAYERS_FILE_PATH = "src/main/resources/files/xml/players.xml";
    private PlayerRepository playerRepository;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;
    private XmlParser xmlParser;
    private TownRepository townRepository;
    private TeamRepository teamRepository;
    private StatRepository statRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, TownRepository townRepository, TeamRepository teamRepository, StatRepository statRepository) {
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.townRepository = townRepository;
        this.teamRepository = teamRepository;
        this.statRepository = statRepository;
    }


    @Override
    public boolean areImported() {
        return playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(Path.of(PLAYERS_FILE_PATH));
    }

    @Override
    public String importPlayers() throws JAXBException, FileNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();

        xmlParser.fromFile(PLAYERS_FILE_PATH, PlayersSeedRootDTO.class)
                .getPlayers()
                .stream()
                .filter(playerSeedDTO -> {
                    boolean isValid = validationUtil.isValid(playerSeedDTO);
                    Optional<Player> playerByEmail = playerRepository.findFirstByEmail(playerSeedDTO.getEmail());
                    if (playerByEmail.isPresent()) {
                        isValid = false;
                    }

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported Player %s %s - %s",
                                    playerSeedDTO.getFirstName(), playerSeedDTO.getLastName(), playerSeedDTO.getPosition())
                                    : "Invalid Player")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(playerSeedDTO -> {
                    Player player = modelMapper.map(playerSeedDTO, Player.class);
                    player.setTown(townRepository.findFirstByName(playerSeedDTO.getTown().getName()).orElse(null));
                    player.setTeam(teamRepository.findByName(playerSeedDTO.getTeam().getName()).orElse(null));
                    player.setStat(statRepository.findById(playerSeedDTO.getStat().getId()).orElse(null));
                    return player;
                })
                .forEach(playerRepository::save);


        return stringBuilder.toString().trim();
    }

    @Override
    public String exportBestPlayers() {
        StringBuilder stringBuilder = new StringBuilder();

        LocalDate from = LocalDate.parse("01-01-1995", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate to = LocalDate.parse("01-01-2003", DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        List<Player> players = playerRepository.findAllByBirthDateGreaterThanAndBirthDateLessThanOrderByStat_ShootingDescStat_PassingDescStat_EnduranceDescLastNameAsc(from, to);

        for (Player player : players) {
            stringBuilder.append(String.format("Player - %s %s%n" +
                            "   Position - %s%n" +
                            "   Team - %s%n" +
                            "   Stadium - %s%n",
                    player.getFirstName(), player.getLastName(),
                    player.getPosition(),
                    player.getTeam().getName(),
                    player.getTeam().getStadiumName()));
        }
        return stringBuilder.toString().trim();
    }
}
