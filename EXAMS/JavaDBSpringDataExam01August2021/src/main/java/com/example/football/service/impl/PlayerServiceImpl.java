package com.example.football.service.impl;

import com.example.football.repository.PlayerRepository;
import com.example.football.service.PlayerService;
import com.example.football.util.ValidationUtil;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PlayerServiceImpl implements PlayerService {
    public static final String PLAYERS_FILE_PATH = "src/main/resources/files/xml/players.xml";
    private PlayerRepository playerRepository;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;

    public PlayerServiceImpl(PlayerRepository playerRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    private XmlParser xmlParser;


    @Override
    public boolean areImported() {
        return playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(Path.of(PLAYERS_FILE_PATH));
    }

    @Override
    public String importPlayers() {
        return null;
    }

    @Override
    public String exportBestPlayers() {
        StringBuilder stringBuilder = new StringBuilder();


        return stringBuilder.toString().trim();
    }
}
