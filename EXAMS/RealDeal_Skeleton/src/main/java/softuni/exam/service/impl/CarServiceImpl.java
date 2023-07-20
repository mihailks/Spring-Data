package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.DTO.CarSeedDTO;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private static final String CARS_FILE_PATH = "src\\main\\resources\\files\\json\\cars.json";
    private final CarRepository carRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public CarServiceImpl(CarRepository carRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.carRepository = carRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return carRepository.count() > 0;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        return Files
                .readString(Path.of(CARS_FILE_PATH));
    }

    @Override
    public String importCars() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
//        CarSeedDTO[] carSeedDTOs = gson
//                .fromJson(readCarsFileContent(), CarSeedDTO[].class);


//        List<Car> cars = Arrays.stream(carSeedDTOs)
//                .filter(validationUtil::isValid)
//                .map(carSeedDTO -> modelMapper.map(carSeedDTO,Car.class))
//                .collect(Collectors.toList());


        Arrays.stream(gson
                        .fromJson(readCarsFileContent(), CarSeedDTO[].class))
                .filter(carSeedDTO -> {
                    boolean isValid = validationUtil.isValid(carSeedDTO);

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported car - %s - %s"
                                    , carSeedDTO.getMake(), carSeedDTO.getModel())
                                    : "Invalid car")
                            .append(System.lineSeparator());


                    return isValid;
                })
                .map(carSeedDTO -> modelMapper.map(carSeedDTO, Car.class))
                .forEach(carRepository::save);

        return stringBuilder.toString();
    }

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {
        StringBuilder stringBuilder = new StringBuilder();

        carRepository.findAllByOrderedByPicturesCountAndName()
                .forEach(car -> {
                    stringBuilder.append(String.format("Car make - %s, model - %s%n",
                                    car.getMake(), car.getModel()))
                            .append(String.format("\tKilometers - %s%n", car.getKilometers()))
                            .append(String.format("\tRegistered on - %s%n", car.getRegisteredOn()))
                            .append(String.format("\tNumber of pictures - %s%n", car.getPictures().size()))
                            .append(System.lineSeparator());
                });

        return stringBuilder.toString().trim();
    }

    @Override
    public Car findById(Long id) {
        return carRepository.findById(id).orElse(null);
    }
}
