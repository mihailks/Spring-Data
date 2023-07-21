package hiberspring.service.impl;

import com.google.gson.Gson;

import hiberspring.domain.dtos.EmployeeCardSeedDTO;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.repository.EmployeeCardRepository;
import hiberspring.service.EmployeeCardService;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class EmployeeCardServiceImpl implements EmployeeCardService {
    public static final String CARD_FILE_NAME = "src\\main\\resources\\files\\employee-cards.json";
    public static final String CARD_FILE_NAME1 = "C:\\Users\\mihai\\Documents\\Spring-Data\\EXAMS\\JavaDBAdvancedExam20Dec2018\\src\\main\\resources\\files\\employee-cards.json";
    private final EmployeeCardRepository employeeCardRepository;
    private Gson gson;
    private ValidationUtil validationUtil;
    private ModelMapper modelMapper;

    public EmployeeCardServiceImpl(EmployeeCardRepository employeeCardRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.employeeCardRepository = employeeCardRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean employeeCardsAreImported() {
        return employeeCardRepository.count() > 0;
    }

    @Override
    public String readEmployeeCardsJsonFile() throws IOException {
        return Files.readString(Path.of(CARD_FILE_NAME));
    }

    @Override
    public String importEmployeeCards(String employeeCardsFileContent) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(gson.fromJson(readEmployeeCardsJsonFile(), EmployeeCardSeedDTO[].class))
                .filter(employeeCardSeedDTO -> {
                    boolean isValid = validationUtil.isValid(employeeCardSeedDTO);

                    Optional<EmployeeCard> byNumber = this.employeeCardRepository
                            .findByNumber(employeeCardSeedDTO.getNumber());

                    if (byNumber.isPresent()) {
                        isValid = false;
                    }

                    stringBuilder.append(isValid
                            ?
                            String.format("Successfully imported Employee Card %s",
                                    employeeCardSeedDTO.getNumber())
                            :
                            "Error: Invalid data."
                    ).append(System.lineSeparator());
                    return isValid;
                })
                .map(EmployeeCardSeedDTO -> modelMapper.map(EmployeeCardSeedDTO, EmployeeCard.class))
                .forEach(employeeCardRepository::save);


        return stringBuilder.toString().trim();
    }

    public Optional<EmployeeCard> findByNumber(String card) {

        return employeeCardRepository.findByNumber(card);
    }
}
