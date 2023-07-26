package softuni.exam.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TownSeedDTO;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class TownServiceImpl implements TownService {
    public static final String TOWNS_FILE_PATH = "C:\\Users\\mihai\\Documents\\Spring-Data\\EXAMS\\JavaDBSpringDataExam02April2022\\skeleton\\src\\main\\resources\\files\\json\\towns.json";
    private TownRepository townRepository;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;
    private Gson gson;

    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWNS_FILE_PATH));
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

//        Arrays.stream(gson.fromJson(readTownsFileContent(), TownSeedDTO[].class))
//                .filter(townSeedDTO -> {
//
//                    boolean isValid = townRepository.findFirstByTownName(townSeedDTO.getTownName()).isEmpty() &&
//                            validationUtil.isValid(townSeedDTO);
//
//                    stringBuilder.append(isValid
//                                    ? String.format("Successfully imported town %s - %d"
//                                    , townSeedDTO.getTownName(), townSeedDTO.getPopulation())
//                                    : "invalid town")
//                            .append(System.lineSeparator());
//
//                    return isValid;
//                })
//                .map(townSeedDTO -> modelMapper.map(townSeedDTO, Town.class))
//                .forEach(townRepository::save);

        Arrays.stream(gson
                        .fromJson(readTownsFileContent(), TownSeedDTO[].class))
                .filter(townSeedDto -> {
                    boolean isValid = validationUtil.isValid(townSeedDto);

                    Optional<Town> townByName = this.townRepository
                            .findByTownName(townSeedDto.getTownName());

                    if (townByName.isPresent()) {
                        isValid = false;
                    }

                    stringBuilder.append(isValid
                            ?
                            String.format("Successfully imported town %s - %d",
                                    townSeedDto.getTownName(), townSeedDto.getPopulation())
                            :
                            "Invalid town"
                    ).append(System.lineSeparator());
                    return isValid;
                })
                .map(townSeedDto -> modelMapper.map(townSeedDto, Town.class))
                .forEach(this.townRepository::save);


        return stringBuilder.toString().trim();
    }
}
