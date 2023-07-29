package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ticket.TicketSeedRootDTO;
import softuni.exam.models.entities.Ticket;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.repository.TicketRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TicketService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
    private final static String TICKETS_FILE_PATH = "src/main/resources/files/xml/tickets.xml";
    private final TicketRepository ticketRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private TownRepository townRepository;
    private PassengerRepository passengerRepository;
    private PlaneRepository planeRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, TownRepository townRepository, PassengerRepository passengerRepository, PlaneRepository planeRepository) {
        this.ticketRepository = ticketRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.townRepository = townRepository;
        this.passengerRepository = passengerRepository;
        this.planeRepository = planeRepository;
    }

    @Override
    public boolean areImported() {
        return ticketRepository.count()>0;
    }

    @Override
    public String readTicketsFileContent() throws IOException {
        return Files.readString(Path.of(TICKETS_FILE_PATH));
    }

    @Override
    public String importTickets() throws JAXBException, FileNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();

        xmlParser.fromFile(TICKETS_FILE_PATH, TicketSeedRootDTO.class)
                .getTickets()
                .stream()
                .filter(ticketSeedDTO -> {
                    boolean isValid = validationUtil.isValid(ticketSeedDTO)
                            && ticketRepository.findBySerialNumber(ticketSeedDTO.getSerialNumber()).isEmpty()
                            && townRepository.findByName(ticketSeedDTO.getFromTown().getName()).isPresent()
                            && townRepository.findByName(ticketSeedDTO.getToTown().getName()).isPresent()
                            && passengerRepository.findByEmail(ticketSeedDTO.getPassenger().getEmail()).isPresent()
                            && planeRepository.findByRegisterNumber(ticketSeedDTO.getPlane().getRegisterNumber()).isPresent();

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported Ticket %s - %s",
                                    ticketSeedDTO.getFromTown().getName(), ticketSeedDTO.getToTown().getName())
                                    : "Invalid Ticket")
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(ticketSeedDTO -> {
                    Ticket ticket = modelMapper.map(ticketSeedDTO, Ticket.class);
                    ticket.setFromTown(townRepository.findByName(ticketSeedDTO.getFromTown().getName()).orElse(null));
                    ticket.setToTown(townRepository.findByName(ticketSeedDTO.getToTown().getName()).orElse(null));
                    ticket.setPassenger(passengerRepository.findByEmail(ticketSeedDTO.getPassenger().getEmail()).orElse(null));
                    ticket.setPlane(planeRepository.findByRegisterNumber(ticketSeedDTO.getPlane().getRegisterNumber()).orElse(null));
                    return ticket;
                })
                .forEach(ticketRepository::save);

        return stringBuilder.toString().trim();
    }
}













