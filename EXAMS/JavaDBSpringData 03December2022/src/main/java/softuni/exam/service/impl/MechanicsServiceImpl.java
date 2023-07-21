package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.MechanicSeedDTO;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.repository.MechanicsRepository;
import softuni.exam.service.MechanicsService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class MechanicsServiceImpl implements MechanicsService {
    public static final String MECHANIC_FILE_PATH = "src/main/resources/files/json/mechanics.json";
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final MechanicsRepository mechanicsRepository;

    public MechanicsServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson, MechanicsRepository mechanicsRepository) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.mechanicsRepository = mechanicsRepository;
    }

    @Override
    public boolean areImported() {
        return mechanicsRepository.count() > 0;
    }

    @Override
    public String readMechanicsFromFile() throws IOException {
        return Files.readString(Path.of(MECHANIC_FILE_PATH));
    }

    @Override
    public String importMechanics() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(gson.fromJson(readMechanicsFromFile(), MechanicSeedDTO[].class))
                .filter(mechanicSeedDTO -> {
                    boolean isValid = validationUtil.isValid(mechanicSeedDTO);

                    Optional<Mechanic> byEmail = mechanicsRepository.findFirstByEmail(mechanicSeedDTO.getEmail());

                    if (byEmail.isPresent()) {
                        isValid = false;
                    }

                    stringBuilder.append(isValid
                            ? String.format("Successfully imported mechanic %s %s",
                            mechanicSeedDTO.getFirstName(), mechanicSeedDTO.getLastName())
                            : "Invalid mechanic")
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(mechanicSeedDTO -> modelMapper.map(mechanicSeedDTO, Mechanic.class))
                .forEach(mechanicsRepository::save);


        return stringBuilder.toString().trim();
    }

    @Override
    public Optional<Mechanic> findByName(String firstName) {
        return mechanicsRepository.findFirstByFirstName(firstName);
    }
}











