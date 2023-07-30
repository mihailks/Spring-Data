package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.StarSeedDTO;
import softuni.exam.models.entity.Star;
import softuni.exam.models.entity.StarType;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.StarService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Repository
public class StarServiceImpl implements StarService {
    private static String STARS_FILE_PATH = "src/main/resources/files/json/stars.json";
    private StarRepository starRepository;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;
    private Gson gson;
    private ConstellationRepository constellationRepository;

    public StarServiceImpl(StarRepository starRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson, ConstellationRepository constellationRepository) {
        this.starRepository = starRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.constellationRepository = constellationRepository;
    }

    @Override
    public boolean areImported() {
        return starRepository.count() > 0;
    }

    @Override
    public String readStarsFileContent() throws IOException {
        return Files.readString(Path.of(STARS_FILE_PATH));
    }

    @Override
    public String importStars() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        Locale.setDefault(new Locale("en", "US"));
        Arrays.stream(gson.fromJson(readStarsFileContent(), StarSeedDTO[].class))
                .filter(starSeedDTO -> {
                    boolean isValid = validationUtil.isValid(starSeedDTO)
                            && starRepository.findByName(starSeedDTO.getName()).isEmpty();

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported star %s - %.2f light years",
                                    starSeedDTO.getName(), starSeedDTO.getLightYears())
                                    : "Invalid star")
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(starSeedDTO -> {
                    Star star = modelMapper.map(starSeedDTO, Star.class);
                    star.setConstellation(constellationRepository.getById(starSeedDTO.getConstellation()));
                    return star;
                })
                .forEach(starRepository::save);


        return stringBuilder.toString().trim();
    }

    @Override
    public String exportStars() {
        StringBuilder stringBuilder = new StringBuilder();

        Locale.setDefault(new Locale("en", "US"));
        List<Star> stars = starRepository.findAllByStarTypeIsAndObserversIsNullOrderByLightYears(StarType.RED_GIANT);
        for (Star star : stars) {

            stringBuilder.append(String.format("Star: %s%n" +
                    "   *Distance: %.2f light years%n" +
                    "   **Description: %s%n" +
                    "   ***Constellation: %s%n",
                    star.getName(),
                    star.getLightYears(),
                    star.getDescription(),
                    star.getConstellation().getName()
                    ));

        }


        return stringBuilder.toString().trim();
    }
}
