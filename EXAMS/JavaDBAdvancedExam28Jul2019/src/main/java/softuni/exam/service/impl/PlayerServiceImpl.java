package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.service.PlayerService;
import softuni.exam.util.ValidatorUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PlayerServiceImpl implements PlayerService {
    public static final String PLAYERS_FILE_PATH = "C:\\Users\\mihai\\Documents\\Spring-Data\\EXAMS\\JavaDBAdvancedExam28Jul2019\\src\\main\\resources\\files\\json\\players.json";
    private PlayerRepository playerRepository;
    private ModelMapper modelMapper;
    private ValidatorUtil validatorUtil;
    private Gson gson;

    public PlayerServiceImpl(PlayerRepository playerRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil, Gson gson) {
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.gson = gson;
    }

    @Override
    public String importPlayers() throws IOException {
        return Files.readString(Path.of(PLAYERS_FILE_PATH));
    }

    @Override
    public boolean areImported() {
        return playerRepository.count() > 0;
    }

    @Override
    public String readPlayersJsonFile() {
        return "";
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        //TODO Implement me
        return "";
    }

    @Override
    public String exportPlayersInATeam() {
        //TODO Implement me
        return "";
    }
}
