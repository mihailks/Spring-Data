package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.domain.dtos.TownSeedDTO;
import hiberspring.domain.entities.Town;
import hiberspring.repository.TownRepository;
import hiberspring.service.TownService;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class TownServiceImpl implements TownService {
    public static final String TOWN_FILE_NAME = "src\\main\\resources\\files\\towns.json";
    private TownRepository townRepository;
    private Gson gson;
    private ValidationUtil validationUtil;
    private ModelMapper modelMapper;

    public TownServiceImpl(TownRepository townRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.townRepository = townRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean townsAreImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        return Files.readString(Path.of(TOWN_FILE_NAME));
    }

    @Override
    public String importTowns(String townsFileContent) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(gson
                        .fromJson(readTownsJsonFile(), TownSeedDTO[].class))
                .filter(townSeedDTO -> {
                    boolean isValid = validationUtil.isValid(townSeedDTO);
                    stringBuilder.append(isValid
                                    ? (String.format("Successfully imported Town %s.", townSeedDTO.getName()))
                                    : "Error: Invalid data.")
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(townSeedDTO -> modelMapper.map(townSeedDTO, Town.class))
                .forEach(townRepository::save);


        return stringBuilder.toString().trim();
    }

    @Override
    public Town findByName(String name) {
        return townRepository.findFirstByNameIs(name);
    }
}
