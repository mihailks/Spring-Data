package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AstronomerSeedDTO;
import softuni.exam.models.dto.AstronomerSeedRootDTO;
import softuni.exam.models.entity.Astronomer;
import softuni.exam.models.entity.Star;
import softuni.exam.repository.AstronomerRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.AstronomerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AstronomerServiceImpl implements AstronomerService {
    public static String ASTRONOMERS_FILE_PATH = "src/main/resources/files/xml/astronomers.xml";
    private AstronomerRepository astronomerRepository;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;
    private XmlParser xmlParser;
    private StarRepository starRepository;

    public AstronomerServiceImpl(AstronomerRepository astronomerRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, StarRepository starRepository) {
        this.astronomerRepository = astronomerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.starRepository = starRepository;
    }

    @Override
    public boolean areImported() {
        return astronomerRepository.count() > 0;
    }

    @Override
    public String readAstronomersFromFile() throws IOException {
        return Files.readString(Path.of(ASTRONOMERS_FILE_PATH));
    }

    @Override
    public String importAstronomers() throws IOException, JAXBException {
        StringBuilder stringBuilder = new StringBuilder();
        Locale.setDefault(new Locale("en", "US"));
//        List<Astronomer> collect = xmlParser.fromFile(ASTRONOMERS_FILE_PATH, AstronomerSeedRootDTO.class)
//                .getAstronomers()
//                .stream()
//                .filter(astronomerSeedDTO -> {
////                    boolean isValid = validationUtil.isValid(astronomerSeedDTO)
////                            && astronomerRepository.findByFirstNameAndLastName(astronomerSeedDTO.getFirstName(), astronomerSeedDTO.getLastName()).isEmpty()
////                            && starRepository.findById(astronomerSeedDTO.getObservingStarId()).isPresent();
//
//                    boolean isValid = validationUtil.isValid(astronomerSeedDTO);
//
//                    Optional<Astronomer> byFirstNameAndLastName = astronomerRepository.findByFirstNameAndLastName(astronomerSeedDTO.getFirstName(), astronomerSeedDTO.getLastName());
//
//                    if (byFirstNameAndLastName.isPresent()){
//                        isValid = false;
//                    }
//                    Optional<Star> byId = starRepository.findById(astronomerSeedDTO.getObservingStarId());
//                    if (byId.isEmpty()){
//                        isValid = false;
//                    }
//
//
//                    stringBuilder.append(isValid
//                                    ? String.format("Successfully imported astronomer %s %s - %.2f",
//                                    astronomerSeedDTO.getFirstName(), astronomerSeedDTO.getLastName(), astronomerSeedDTO.getAverageObservationHours())
//                                    : "Invalid astronomer")
//                            .append(System.lineSeparator());
//                    return isValid;
//                })
//                .map(astronomerSeedDTO -> {
//                    Astronomer astronomer = modelMapper.map(astronomerSeedDTO, Astronomer.class);
//                    astronomer.setObservingStar(starRepository.findById(astronomerSeedDTO.getObservingStarId()).orElse(null));
//                    return astronomer;
//                }).collect(Collectors.toList());


//                .forEach(astronomerRepository::save);

        List<AstronomerSeedDTO> astronomers = xmlParser.fromFile(ASTRONOMERS_FILE_PATH, AstronomerSeedRootDTO.class).getAstronomers();
        for (AstronomerSeedDTO astronomerSeedDTO : astronomers) {
            boolean isValid = validationUtil.isValid(astronomerSeedDTO);

            Optional<Astronomer> byFirstNameAndLastName =
                    astronomerRepository.findByFirstNameAndLastName(astronomerSeedDTO.getFirstName(), astronomerSeedDTO.getLastName());
            if (byFirstNameAndLastName.isPresent()) {
                isValid = false;
            }

            Optional<Star> byId = starRepository.findById(astronomerSeedDTO.getObservingStarId());
            if (byId.isEmpty()) {
                isValid = false;
            }
            stringBuilder.append(isValid
                            ? String.format("Successfully imported astronomer %s %s - %.2f",
                            astronomerSeedDTO.getFirstName(), astronomerSeedDTO.getLastName(), astronomerSeedDTO.getAverageObservationHours())
                            : "Invalid astronomer")
                    .append(System.lineSeparator());
            if (isValid){
                Astronomer astronomer = modelMapper.map(astronomerSeedDTO, Astronomer.class);
                astronomer.setObservingStar(starRepository.findById(astronomerSeedDTO.getObservingStarId()).orElse(null));
                astronomerRepository.save(astronomer);
            }

        }


        return stringBuilder.toString().trim();
    }
}
