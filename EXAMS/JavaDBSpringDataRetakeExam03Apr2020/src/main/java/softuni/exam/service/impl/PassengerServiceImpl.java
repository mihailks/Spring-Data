package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PassengerServiceImpl implements PassengerService {
    private final static String PASSENGERS_FILE_PATH = "src/main/resources/files/json/passengers.json";
    private final PassengerRepository passengerRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public PassengerServiceImpl(PassengerRepository passengerRepository, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.passengerRepository = passengerRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return passengerRepository.count()>0;
    }

    @Override
    public String readPassengersFileContent() throws IOException {
        return Files.readString(Path.of(PASSENGERS_FILE_PATH));
    }

    @Override
    public String importPassengers() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();



        return stringBuilder.toString().trim();
    }

    @Override
    public String getPassengersOrderByTicketsCountDescendingThenByEmail() {
        StringBuilder stringBuilder = new StringBuilder();



        return stringBuilder.toString().trim();
    }
}
