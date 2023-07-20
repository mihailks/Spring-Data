package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.DTO.offer.OfferSeedRootDTO;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.OfferService;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class OfferServiceImpl implements OfferService {
    private static final String OFFERS_FILE_PATH = "src/main/resources/files/xml/offers.xml";

    private final OfferRepository offerRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final CarService carService;
    private final SellerService sellerService;

    public OfferServiceImpl(OfferRepository offerRepository, ValidationUtil validationUtil, ModelMapper modelMapper, XmlParser xmlParser, CarService carService, SellerService sellerService) {
        this.offerRepository = offerRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.carService = carService;
        this.sellerService = sellerService;
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
    public String importOffers() throws IOException, JAXBException {

        StringBuilder stringBuilder = new StringBuilder();

        xmlParser
                .fromFile(OFFERS_FILE_PATH, OfferSeedRootDTO.class)
                .getOffers()
                .stream()
                .filter(offerSeedDTO -> {
                    boolean isValid = validationUtil.isValid(offerSeedDTO);

                    stringBuilder
                            .append(isValid ? String.format("Successfully import offer %s - %s",
                                    offerSeedDTO.getAddedOn(),
                                    offerSeedDTO.getHasGoldStatus())
                                    : "Invalid offer")
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(offerSeedDTO -> {
                    Offer offer = modelMapper.map(offerSeedDTO, Offer.class);
                    offer.setCar(carService.findById(offerSeedDTO.getCar().getId()));
                    offer.setSeller(sellerService.findById(offerSeedDTO.getSeller().getId()));
                    return offer;
                })
                .forEach(offerRepository::save);


        return stringBuilder.toString().trim();
    }
}















