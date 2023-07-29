package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ExportPassengerDto;
import softuni.exam.models.dto.PassengerSeedDTO;
import softuni.exam.models.entities.Passenger;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Service
public class PassengerServiceImpl implements PassengerService {
    private final static String PASSENGERS_FILE_PATH = "src/main/resources/files/json/passengers.json";
    private final PassengerRepository passengerRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private TownService townService;

    public PassengerServiceImpl(PassengerRepository passengerRepository, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson, TownService townService) {
        this.passengerRepository = passengerRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.townService = townService;
    }

    @Override
    public boolean areImported() {
        return passengerRepository.count() > 0;
    }

    @Override
    public String readPassengersFileContent() throws IOException {
        return Files.readString(Path.of(PASSENGERS_FILE_PATH));
    }

    @Override
    public String importPassengers() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(gson.fromJson(readPassengersFileContent(), PassengerSeedDTO[].class))
                .filter(passengerSeedDTO -> {
                    boolean isValid = validationUtil.isValid(passengerSeedDTO)
                            && passengerRepository.findByEmail(passengerSeedDTO.getEmail()).isEmpty();

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported Passenger %s - %s",
                                    passengerSeedDTO.getLastName(), passengerSeedDTO.getEmail())
                                    : "Invalid Passenger")
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(passengerSeedDTO -> {
                    Passenger passenger = modelMapper.map(passengerSeedDTO, Passenger.class);
                    passenger.setTown(townService.findByName(passengerSeedDTO.getTown()).orElse(null));
                    return passenger;
                })
                .forEach(passengerRepository::save);

        return stringBuilder.toString().trim();
    }

    @Override
    public String getPassengersOrderByTicketsCountDescendingThenByEmail() {
        StringBuilder stringBuilder = new StringBuilder();

//        List<PassengerExportDTO> print = passengerRepository.findAllByTicketsCount();
        List<ExportPassengerDto> print = passengerRepository.exportPassenger();

        for (ExportPassengerDto passenger : print) {
            stringBuilder.append(String.format(
                    "Passenger %s  %s%n" +
                    "\tEmail - %s%n" +
                    "\tPhone - %s%n" +
                    "\tNumber of tickets - %d%n",
                    passenger.getFirstName(), passenger.getLastName(),
                    passenger.getEmail(),
                    passenger.getPhoneNumber(),
                    passenger.getCountTickets()));
        }


        return stringBuilder.toString().trim();
    }
}
