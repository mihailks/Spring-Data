package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ForecastSeedDTO;
import softuni.exam.models.dto.ForecastSeedRootDTO;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.DayOfWeek;
import softuni.exam.models.entity.Forecast;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.ForecastService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ForecastServiceImpl implements ForecastService {
    public static final String FORECAST_FILE_PATH = "C:\\Users\\mihai\\Documents\\Spring-Data\\EXAMS\\JavaDBSpringDataRetakeExam17April2022\\src\\main\\resources\\files\\xml\\forecasts.xml";

    private final ForecastRepository forecastRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    private final CityRepository cityRepository;

    public ForecastServiceImpl(ForecastRepository forecastRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, CityRepository cityRepository) {
        this.forecastRepository = forecastRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.cityRepository = cityRepository;
    }


    @Override
    public boolean areImported() {
        return forecastRepository.count() > 0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return Files.readString(Path.of(FORECAST_FILE_PATH));
    }

    @Override
    public String importForecasts() throws IOException, JAXBException {
        StringBuilder stringBuilder = new StringBuilder();

        List<ForecastSeedDTO> forecastSeedDTOs = xmlParser.fromFile(FORECAST_FILE_PATH, ForecastSeedRootDTO.class)
                .getForecasts();

        for (ForecastSeedDTO forecastSeedDTO : forecastSeedDTOs) {
            stringBuilder.append(System.lineSeparator());

            if (!validationUtil.isValid(forecastSeedDTO)) {
                stringBuilder.append("Invalid task");
                continue;
            }

            DayOfWeek dayOfWeek = forecastSeedDTO.getDayOfWeek();
            Optional<Forecast> byDayOfWeekAndCity = forecastRepository
                    .findFirstByCity_IdAndDayOfWeek(forecastSeedDTO.getCity(), dayOfWeek);

            if (byDayOfWeekAndCity.isPresent()) {
                stringBuilder.append("Invalid task");
                continue;
            }

            Forecast forecast = modelMapper.map(forecastSeedDTO, Forecast.class);
            forecast.setCity(cityRepository.findById(forecastSeedDTO.getCity()).orElse(null));

            forecastRepository.save(forecast);

            stringBuilder.append(String.format("Successfully import forecast %s - %s",
                    forecastSeedDTO.getDayOfWeek(), forecastSeedDTO.getMaxTemperature()));

        }
        return stringBuilder.toString().trim();
    }

    @Override
    public String exportForecasts() {
        StringBuilder stringBuilder = new StringBuilder();

        Set<Forecast> allByDayOfWeekAndCityPopulation = forecastRepository.findAllByDayOfWeekAndCity_PopulationLessThanOrderByMaxTemperatureDescIdAsc(DayOfWeek.SUNDAY, 150000);

        allByDayOfWeekAndCityPopulation
                .forEach(f -> {
                    stringBuilder.append(String.format("City: %s\n" +
                                            "-min temperature: %.2f\n" +
                                            "--max temperature: %.2f\n" +
                                            "---sunrise: %s\n" +
                                            "-----sunset: %s",
                                    f.getCity().getCityName(),
                                    f.getMinTemperature(),
                                    f.getMaxTemperature(),
                                    f.getSunrise(),
                                    f.getSunset()))
                            .append(System.lineSeparator());
                });

        return stringBuilder.toString().trim();
    }
}











