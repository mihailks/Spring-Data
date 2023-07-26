package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.Offer.OfferSeedRootDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.ApartmentType;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService {
    public static final String OFFERS_FILE_PATH = "C:\\Users\\mihai\\Documents\\Spring-Data\\EXAMS\\JavaDBSpringDataExam02April2022\\skeleton\\src\\main\\resources\\files\\xml\\offers.xml";
    private OfferRepository offerRepository;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;
    private XmlParser xmlParser;
    private AgentRepository agentRepository;
    private ApartmentRepository apartmentRepository;

    public OfferServiceImpl(OfferRepository offerRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, AgentRepository agentRepository, ApartmentRepository apartmentRepository) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.agentRepository = agentRepository;
        this.apartmentRepository = apartmentRepository;
    }

    @Override
    public boolean areImported() {
        return offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(OFFERS_FILE_PATH));
    }

    @Override
    public String importOffers() throws JAXBException, IOException {
        StringBuilder stringBuilder = new StringBuilder();

//        xmlParser.fromFile(OFFERS_FILE_PATH, OfferSeedRootDTO.class)
//                .getOffers()
//                .stream()
//                .filter(offerSeedDTO -> {
//                    boolean isValid = validationUtil.isValid(offerSeedDTO);
//
//                    if (agentRepository.findByFirstName(offerSeedDTO
//                            .getAgentNameDTO().getName()).isEmpty()) {
//                        isValid = false;
//                    }
//
//                    stringBuilder.append(isValid
//                                    ? String.format("Successfully imported offer %s", offerSeedDTO.getPrice().setScale(2))
//                                    : "invalid offer")
//                            .append(System.lineSeparator());
//                    return isValid;
//                })
//                .map(offerSeedDTO -> {
//                    Offer offer = modelMapper.map(offerSeedDTO, Offer.class);
//                    offer.setAgent(agentRepository.findByFirstName(offerSeedDTO.getAgentNameDTO().getName()).orElse(null));
//                    offer.setApartment(apartmentRepository.findById(offerSeedDTO.getApartmentIdDTO().getId()).orElse(null));
//                    return offer;
//                })
//                .forEach(offerRepository::save);



        xmlParser
                .fromFile(OFFERS_FILE_PATH, OfferSeedRootDTO.class)
                .getOffers()
                .stream()
                .filter(offerSeedDTO -> {
                    boolean isValid = validationUtil.isValid(offerSeedDTO);

                    Optional<Agent> agentByName = this.agentRepository
                            .findByFirstName(offerSeedDTO.getAgentNameDTO().getName());

                    if (agentByName.isEmpty()) {
                        isValid = false;
                    }

                    stringBuilder.append(isValid
                            ?
                            String.format("Successfully imported offer %.2f",
                                    offerSeedDTO.getPrice())
                            :
                            "Invalid offer"
                    ).append(System.lineSeparator());
                    return isValid;
                })
                .map(offerSeedDto -> {
                    Offer offer = modelMapper.map(offerSeedDto, Offer.class);
                    Optional<Agent> agentByName = this.agentRepository
                            .findByFirstName(offerSeedDto.getAgentNameDTO().getName());
                    Optional<Apartment> apartmentById = this.apartmentRepository
                            .findById(offerSeedDto.getApartmentIdDTO().getId());
                    offer.setAgent(agentByName.get());
                    offer.setApartment(apartmentById.get());
                    return offer;
                })
                .forEach(this.offerRepository::save);


        return stringBuilder.toString().trim();
    }

    public String exportOffers() {
        StringBuilder stringBuilder = new StringBuilder();

        List<Offer> printOffer = offerRepository
                .findAllByApartment_ApartmentTypeOrderByApartment_AreaDescPrice(ApartmentType.three_rooms);

        for (Offer offer : printOffer) {
            stringBuilder.append(String.format("Agent %s %s with offer №%d:%n" +
                            "-Apartment area: %.2f%n" +
                            "--Town: %s%n" +
                            "---Price: %s$%n",
                    offer.getAgent().getFirstName(),
                    offer.getAgent().getLastName(),
                    offer.getId(),
                    offer.getApartment().getArea(),
                    offer.getApartment().getTown().getTownName(),
                    offer.getPrice().setScale(2)
            ));

        }

        return stringBuilder.toString().trim();
    }
}
