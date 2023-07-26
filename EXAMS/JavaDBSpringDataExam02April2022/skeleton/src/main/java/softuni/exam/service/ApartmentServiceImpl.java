package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ApartmentSeedRootDTO;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class ApartmentServiceImpl implements ApartmentService {
    public static final String APARTMENTS_FILE_PATH = "C:\\Users\\mihai\\Documents\\Spring-Data\\EXAMS\\JavaDBSpringDataExam02April2022\\skeleton\\src\\main\\resources\\files\\xml\\apartments.xml";
    private ApartmentRepository apartmentRepository;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;
    private XmlParser xmlParser;
    private TownRepository townRepository;

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, TownRepository townRepository) {
        this.apartmentRepository = apartmentRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(Path.of(APARTMENTS_FILE_PATH));
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        StringBuilder stringBuilder = new StringBuilder();

//        xmlParser.fromFile(APARTMENTS_FILE_PATH, ApartmentSeedRootDTO.class)
//                .getApartments()
//                .stream()
//                .filter(apartmentSeedDTO -> {
//                    boolean isValid = validationUtil.isValid(apartmentSeedDTO);
//
//                    Optional<Apartment> apartmentByAreaAndTown = apartmentRepository
//                            .findFirstByAreaAndTown_TownName(apartmentSeedDTO.getArea(), apartmentSeedDTO.getTown());
//
//                    if (apartmentByAreaAndTown.isPresent()) {
//                        isValid = false;
//                    }
//
//                    stringBuilder.append(isValid
//                                    ? String.format("Successfully imported apartment %s - %.2f",
//                                    apartmentSeedDTO.getApartmentType(), apartmentSeedDTO.getArea())
//                                    : "Invalid apartment")
//                            .append(System.lineSeparator());
//
//                    return isValid;
//                })
//                .map(apartmentSeedDTO -> {
//                    Apartment apartment = modelMapper.map(apartmentSeedDTO, Apartment.class);
//                    apartment.setTown(townRepository.findFirstByTownName(apartmentSeedDTO.getTown()).orElse(null));
//                    return apartment;
//                })
//                .forEach(apartmentRepository::save);


        xmlParser
                .fromFile(APARTMENTS_FILE_PATH, ApartmentSeedRootDTO.class)
                .getApartments()
                .stream()
                .filter(apartmentSeedDTO -> {
                    boolean isValid = this.validationUtil.isValid(apartmentSeedDTO);

                    boolean ifExist = this.apartmentRepository
                            .findApartmentByAreaAndTown(apartmentSeedDTO.getArea(),
                                    this.townRepository
                                            .findByTownName(apartmentSeedDTO.getTown()))
                            .isEmpty();
                    if (!ifExist) {
                        isValid = false;
                    }

                    stringBuilder.append(isValid
                            ?
                            String.format("Successfully imported apartment %s - %.2f",
                                    apartmentSeedDTO.getApartmentType(), apartmentSeedDTO.getArea())
                            :
                            "Invalid apartment"
                    ).append(System.lineSeparator());
                    return isValid;
                })
                .map(apartmentSeedDto -> {
                    Apartment apartment = modelMapper.map(apartmentSeedDto, Apartment.class);
                    Optional<Town> townByName = this.townRepository
                            .findByTownName(apartmentSeedDto.getTown());
                    apartment.setTown(townByName.get());
                    return apartment;
                })
                .forEach(this.apartmentRepository::save);


        return stringBuilder.toString().trim();
    }
}













