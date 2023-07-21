package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CarSeedDTO;
import softuni.exam.models.dto.CarSeedRootDTO;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarsRepository;
import softuni.exam.service.CarsService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class CarsServiceImpl implements CarsService {
    public static String CARS_FILE_PATH = "src/main/resources/files/xml/cars.xml";
    public static String CARS_FILE_PATH2 = "C:\\Users\\mihai\\Documents\\Spring-Data\\EXAMS\\JavaDBSpringData 03December2022\\src\\main\\resources\\files\\xml\\cars.xml";
    public static String CARS_FILE_PATH3 = "src\\main\\resources\\files\\xml\\cars.xml";
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final CarsRepository carsRepository;

    public CarsServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, CarsRepository carsRepository) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.carsRepository = carsRepository;
    }

    @Override
    public boolean areImported() {
        return carsRepository.count() > 0;
    }

    @Override
    public String readCarsFromFile() throws IOException {
        return Files.readString(Path.of(CARS_FILE_PATH));
    }

    @Override
    public String importCars() throws IOException, JAXBException {
        StringBuilder stringBuilder = new StringBuilder();


//        final List<CarSeedDTO> cars =
//                this.xmlParser
//                        .fromFile(CAR_FILE_PATH, CarSeedRootDTO.class)
//                        .getCars();
//        for (CarSeedDTO carSeedDTO : cars) {
//            Car car = modelMapper.map(carSeedDTO, Car.class);
//            carsRepository.save(car);
//        }



        xmlParser.fromFile(CARS_FILE_PATH, CarSeedRootDTO.class)
                .getCars()
                .stream()
                .filter(carSeedDTO -> {

                    if (carsRepository.findFirstByPlateNumber(carSeedDTO.getPlateNumber()).isPresent() ||
                            !validationUtil.isValid(carSeedDTO)) {
                        stringBuilder.append("Invalid car")
                                .append(System.lineSeparator());
                        return false;
                    } else {
                        stringBuilder.append(String.format("Successfully imported car %s - %s",
                                        carSeedDTO.getCarMake(), carSeedDTO.getCarModel()))
                                .append(System.lineSeparator());
                    }
                    return true;
                })
                .map(carRootDTO -> modelMapper.map(carRootDTO, Car.class))
                .forEach(carsRepository::save);


        return stringBuilder.toString().trim();
    }

    @Override
    public Car findByID(Long id) {
        return carsRepository.findById(id).orElse(null);
    }
}












