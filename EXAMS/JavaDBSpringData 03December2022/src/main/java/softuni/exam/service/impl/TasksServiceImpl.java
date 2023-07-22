package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TaskSeedDTO;
import softuni.exam.models.dto.TaskSeedRootDTO;
import softuni.exam.models.entity.CarType;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.models.entity.Task;
import softuni.exam.repository.TasksRepository;
import softuni.exam.service.CarsService;
import softuni.exam.service.MechanicsService;
import softuni.exam.service.PartsService;
import softuni.exam.service.TasksService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static java.math.BigDecimal.ROUND_HALF_UP;

@Service
public class TasksServiceImpl implements TasksService {
    public static String TASKS_FILE_PATH = "src/main/resources/files/xml/tasks.xml";
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final TasksRepository tasksRepository;
    private final MechanicsService mechanicsService;
    private final CarsService carsService;
    private final PartsService partsService;


    public TasksServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, TasksRepository tasksRepository, MechanicsService mechanicsService, CarsService carsService, PartsService partsService) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.tasksRepository = tasksRepository;
        this.mechanicsService = mechanicsService;
        this.carsService = carsService;
        this.partsService = partsService;
    }

    @Override
    public boolean areImported() {
        return tasksRepository.count() > 0;
    }

    @Override
    public String readTasksFileContent() throws IOException {
        return Files.readString(Path.of(TASKS_FILE_PATH));
    }

    @Override
    public String importTasks() throws IOException, JAXBException {
        StringBuilder stringBuilder = new StringBuilder();

        List<TaskSeedDTO> tasks = xmlParser.fromFile(TASKS_FILE_PATH, TaskSeedRootDTO.class)
                .getTasks();

        for (TaskSeedDTO taskSeedDTO : tasks) {
            stringBuilder.append(System.lineSeparator());
            Optional<Mechanic> mechanic = mechanicsService.findByName(taskSeedDTO.getMechanic().getFirstName());

            if (!validationUtil.isValid(taskSeedDTO) || mechanic.isEmpty()) {
                stringBuilder.append("Invalid task");
                continue;
            }

            Task task = modelMapper.map(taskSeedDTO, Task.class);
            task.setCar(carsService.findByID(taskSeedDTO.getCar().getId()));
            task.setMechanic(mechanic.orElse(null));
            task.setPart(partsService.findByID(taskSeedDTO.getPart().getId()));

            tasksRepository.save(task);


            BigDecimal formatBD = new BigDecimal(String.valueOf(task.getPrice()));
            formatBD.setScale(2, ROUND_HALF_UP); // this does change bd
            formatBD = formatBD.setScale(2, ROUND_HALF_UP);


            stringBuilder.append(String.format("Successfully imported task %s", formatBD));

        }


        return stringBuilder.toString().trim();
    }

    @Override
    public String getCoupeCarTasksOrderByPrice() {
        List<Task> tasks = tasksRepository.findAllByCar_CarTypeOrderByPriceDesc(CarType.coupe);
        StringBuilder stringBuilder = new StringBuilder();
        Locale.setDefault(new Locale("en", "US"));
        for (Task task : tasks) {



            stringBuilder.append(String.format("Car %s %s with %skm\n" +
                    "-Mechanic: %s %s - task №%d:\n" +
                    " --Engine: %.1f\n" +
                    "---Price: %.2f$\n",
                            task.getCar().getCarMake(),
                            task.getCar().getCarModel(),
                            task.getCar().getKilometers(),
                    task.getMechanic().getFirstName(),
                    task.getMechanic().getLastName(),
                    task.getId(),
                    task.getCar().getEngine(),
                    task.getPrice().setScale(2)));



        }


        return stringBuilder.toString().trim();
    }
}














