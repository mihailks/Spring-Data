package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountrySeedDTo;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class CountryServiceImpl implements CountryService {

    public static final String COUNTY_FILE_PATH = "C:\\Users\\mihai\\Documents\\Spring-Data\\EXAMS\\JavaDBSpringDataRetakeExam17April2022\\src\\main\\resources\\files\\json\\countries.json";
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(Path.of(COUNTY_FILE_PATH));
    }

    @Override
    public String importCountries() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(gson
                        .fromJson(readCountriesFromFile(), CountrySeedDTo[].class))
                .filter(countrySeedDTo -> {
                    boolean isValid = validationUtil.isValid(countrySeedDTo) &&
                            countryRepository.findFirstByCountryName(countrySeedDTo.getCountryName()).isEmpty();

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported country %s - %s",
                                    countrySeedDTo.getCountryName(), countrySeedDTo.getCurrency())
                                    : "Invalid country")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(countrySeedDTo -> modelMapper.map(countrySeedDTo, Country.class))
                .forEach(countryRepository::save);


        return stringBuilder.toString().trim();
    }
}
