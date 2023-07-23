package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.repository.TeamRepository;
import softuni.exam.service.TeamService;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class TeamServiceImpl implements TeamService {
    public static final String TEAMS_FILE_PATH = "C:\\Users\\mihai\\Documents\\Spring-Data\\EXAMS\\JavaDBAdvancedExam28Jul2019\\src\\main\\resources\\files\\xml\\teams.xml";
    private TeamRepository teamRepository;
    private ModelMapper modelMapper;
    private ValidatorUtil validatorUtil;
    private XmlParser xmlParser;

    public TeamServiceImpl(TeamRepository teamRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil, XmlParser xmlParser) {
        this.teamRepository = teamRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public String importTeams() throws IOException {

        return Files.readString(Path.of(TEAMS_FILE_PATH));
    }

    @Override
    public boolean areImported() {
        return teamRepository.count()>0;
    }

    @Override
    public String readTeamsXmlFile() {
        //TODO Implement me
        return "";
    }
}
