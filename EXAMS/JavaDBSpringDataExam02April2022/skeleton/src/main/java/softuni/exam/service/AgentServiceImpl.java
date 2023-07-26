package softuni.exam.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AgentSeedDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class AgentServiceImpl implements AgentService {
    public static final String AGENTS_FILE_PATH = "C:\\Users\\mihai\\Documents\\Spring-Data\\EXAMS\\JavaDBSpringDataExam02April2022\\skeleton\\src\\main\\resources\\files\\json\\agents.json";
    private AgentRepository agentRepository;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;
    private Gson gson;
    private TownRepository townRepository;

    public AgentServiceImpl(AgentRepository agentRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson, TownRepository townRepository) {
        this.agentRepository = agentRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.townRepository = townRepository;
    }


    @Override
    public boolean areImported() {
        return agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(Path.of(AGENTS_FILE_PATH));
    }

    @Override
    public String importAgents() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

//        Arrays.stream(gson.fromJson(readAgentsFromFile(), AgentSeedDTO[].class))
//                .filter(agentSeedDTO -> {
//
//                    boolean isValid = validationUtil.isValid(agentSeedDTO);
//
//                    Optional<Agent> agentByName = agentRepository.findFirstByFirstName(agentSeedDTO.getFirstName());
//
//                    if (agentByName.isPresent()) {
//                        isValid = false;
//                    }
//
////                    Optional<Agent> agentByEmail = agentRepository.findFirstByEmail(agentSeedDTO.getEmail());
////
////                    if (agentByEmail.isPresent()) {
////                        isValid = false;
////                    }
//
//                    stringBuilder.append(isValid
//                            ?
//                            String.format("Successfully imported agent - %s %s",
//                                    agentSeedDTO.getFirstName(), agentSeedDTO.getLastName())
//                            :
//                            "Invalid agent"
//                    ).append(System.lineSeparator());
//                    return isValid;
//                })
//                .map(agentSeedDTO -> {
//                    Agent agent = modelMapper.map(agentSeedDTO, Agent.class);
//                    agent.setTown(townRepository.findFirstByTownName(agentSeedDTO.getTown()).orElse(null));
//                    return agent;
//                })
//                .forEach(this.agentRepository::save);

        Arrays.stream(gson
                        .fromJson(readAgentsFromFile(), AgentSeedDTO[].class))
                .filter(agentSeedDto -> {
                    boolean isValid = validationUtil.isValid(agentSeedDto);

                    Optional<Agent> agentByName = this.agentRepository
                            .findByFirstName(agentSeedDto.getFirstName());

                    if (agentByName.isPresent()) {
                        isValid = false;
                    }

                    stringBuilder.append(isValid
                            ?
                            String.format("Successfully imported agent - %s %s",
                                    agentSeedDto.getFirstName(), agentSeedDto.getLastName())
                            :
                            "Invalid agent"
                    ).append(System.lineSeparator());
                    return isValid;
                })
                .map(agentSeedDto -> {
                    Agent agent = modelMapper.map(agentSeedDto, Agent.class);
                    Optional<Town> townByName = townRepository
                            .findByTownName(agentSeedDto.getTown());
                    agent.setTown(townByName.get());
                    return agent;
                })
                .forEach(this.agentRepository::save);





        return stringBuilder.toString().trim();
    }
}
