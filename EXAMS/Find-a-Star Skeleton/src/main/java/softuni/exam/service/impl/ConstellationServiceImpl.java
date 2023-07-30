package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.ConstellationSeedDTO;
import softuni.exam.models.entity.Constellation;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.service.ConstellationService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Repository
public class ConstellationServiceImpl implements ConstellationService {
    private static String CONSTELLATIONS_FILE_PATH = "src/main/resources/files/json/constellations.json";
    private ConstellationRepository constellationRepository;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;
    private Gson gson;

    public ConstellationServiceImpl(ConstellationRepository constellationRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.constellationRepository = constellationRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return constellationRepository.count()>0;
    }

    @Override
    public String readConstellationsFromFile() throws IOException {
        return Files.readString(Path.of(CONSTELLATIONS_FILE_PATH));
    }

    @Override
    public String importConstellations() throws IOException {
       StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(gson.fromJson(readConstellationsFromFile(), ConstellationSeedDTO[].class))
                .filter(constellationSeedDTO -> {
                    boolean isValid = validationUtil.isValid(constellationSeedDTO)
                            && constellationRepository.findByName(constellationSeedDTO.getName()).isEmpty();

                    stringBuilder.append(isValid
                    ? String.format("Successfully imported constellation %s - %s",
                            constellationSeedDTO.getName(), constellationSeedDTO.getDescription())
                            : "Invalid constellation")
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(constellationSeedDTO -> modelMapper.map(constellationSeedDTO, Constellation.class))
                .forEach(constellationRepository::save);


        return stringBuilder.toString().trim();
    }
}














