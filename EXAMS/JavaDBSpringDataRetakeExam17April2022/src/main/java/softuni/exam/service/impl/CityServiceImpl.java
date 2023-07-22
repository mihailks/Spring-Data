package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CitySeedDTO;
import softuni.exam.models.entity.City;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CityService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class CityServiceImpl implements CityService {
    public static final String CITY_FILE_PATH = "C:\\Users\\mihai\\Documents\\Spring-Data\\EXAMS\\JavaDBSpringDataRetakeExam17April2022\\src\\main\\resources\\files\\json\\cities.json";
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final CountryRepository countryRepository;

    public CityServiceImpl(CityRepository cityRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson, CountryRepository countryRepository) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.countryRepository = countryRepository;
    }

    @Override
    public boolean areImported() {
        return cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(Path.of(CITY_FILE_PATH));
    }

    @Override
    public String importCities() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(gson
                        .fromJson(readCitiesFileContent(), CitySeedDTO[].class))
                .filter(citySeedDTO -> {
                    boolean isValid = validationUtil.isValid(citySeedDTO) &&
                            cityRepository.findFirstByCityName(citySeedDTO.getCityName()).isEmpty();

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported city %s - %d",
                                    citySeedDTO.getCityName(), citySeedDTO.getPopulation())
                                    : "Invalid city")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(citySeedDTO -> {
                    City city = modelMapper.map(citySeedDTO, City.class);
                    city.setCountry(countryRepository.getById(citySeedDTO.getCountry()));
                    return city;
                })

                .forEach(cityRepository::save);


        return stringBuilder.toString().trim();
    }
}
