package hiberspring.service.impl;


import hiberspring.domain.dtos.employees.EmployeeSeedRootDTO;
import hiberspring.domain.entities.Employee;
import hiberspring.repository.EmployeeRepository;
import hiberspring.service.BranchService;
import hiberspring.service.EmployeeCardService;
import hiberspring.service.EmployeeService;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    public static final String EMPLOYEES_FILE_NAME = "C:\\Users\\mihai\\Documents\\Spring-Data\\EXAMS\\JavaDBAdvancedExam20Dec2018\\src\\main\\resources\\files\\employees.xml";
    private final EmployeeRepository employeeRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final EmployeeCardService employeeCardService;
    private final BranchService branchService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil, EmployeeCardService employeeCardService, BranchService branchService) {
        this.employeeRepository = employeeRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.employeeCardService = employeeCardService;
        this.branchService = branchService;
    }

    @Override
    public Boolean employeesAreImported() {
        return employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return Files.readString(Path.of(EMPLOYEES_FILE_NAME));
    }

    @Override
    public String importEmployees() throws JAXBException, FileNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();

        xmlParser.parseXml(EmployeeSeedRootDTO.class, EMPLOYEES_FILE_NAME)
                .getEmployees()
                .stream()
                .filter(employeeSeedDTO -> {
                    boolean isValid = validationUtil.isValid(employeeSeedDTO);
                    Optional<Employee> byNumber = this.employeeRepository.findByCard_Number(employeeSeedDTO.getCard());
                    if (byNumber.isEmpty()) {
                        isValid = false;
                    }

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported Employee %s %s."
                                    , employeeSeedDTO.getFirstName(), employeeSeedDTO.getLastName())
                                    : "invalid data!")
                            .append(System.lineSeparator());
                    return isValid;

                })
                .map(employeeSeedDTO -> {
                    Employee employee = modelMapper.map(employeeSeedDTO, Employee.class);
                    employee.setCard(employeeCardService.findByCardNumber(employeeSeedDTO.getCard()).orElse(null));
                    employee.setBranch(branchService.findByName(employeeSeedDTO.getBranch()));
                    return employee;
                })
                .forEach(employeeRepository::save);


        return stringBuilder.toString().trim();
    }

    @Override
    public String exportProductiveEmployees() {
        return null;
    }
}











