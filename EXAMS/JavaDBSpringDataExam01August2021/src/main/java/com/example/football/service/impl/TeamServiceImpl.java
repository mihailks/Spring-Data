package com.example.football.service.impl;

import com.example.football.models.dto.TeamSeedDTO;
import com.example.football.models.entity.Team;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
import com.example.football.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class TeamServiceImpl implements TeamService {
    public static final String TEAMS_FILE_PATH = "src/main/resources/files/json/teams.json";
    private TeamRepository teamRepository;
    private ModelMapper modelMapper;
    private Gson gson;
    private ValidationUtil validationUtil;
    private TownRepository townRepository;

    public TeamServiceImpl(TeamRepository teamRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil, TownRepository townRepository) {
        this.teamRepository = teamRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.townRepository = townRepository;
    }


    @Override
    public boolean areImported() {
        return teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(Path.of(TEAMS_FILE_PATH));
    }

    @Override
    public String importTeams() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(gson.fromJson(readTeamsFileContent(), TeamSeedDTO[].class))
                .filter(teamSeedDTO -> {
                    boolean isValid = validationUtil.isValid(teamSeedDTO) &&
                            teamRepository.findByName(teamSeedDTO.getName()).isEmpty();

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported Team %s - %d", teamSeedDTO.getName(), teamSeedDTO.getFanBase())
                                    : "Invalid Town")
                            .append(System.lineSeparator());


                    return isValid;
                })
                .map(teamSeedDTO -> {
                    Team team = modelMapper.map(teamSeedDTO, Team.class);
                    team.setTown(townRepository.findFirstByName(teamSeedDTO.getTownName()).orElse(null));
                    return team;
                })
                .forEach(teamRepository::save);


        return stringBuilder.toString().trim();
    }
}
