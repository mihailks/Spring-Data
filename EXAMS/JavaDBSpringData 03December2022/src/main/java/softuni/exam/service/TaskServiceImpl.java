package softuni.exam.service;

import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
@Service
public class TaskServiceImpl implements TaskService {
    @Override
    public boolean areImported() {
        return false;
    }

    @Override
    public String readTasksFileContent() throws IOException {
        return null;
    }

    @Override
    public String importTasks() throws IOException, JAXBException {
        return null;
    }

    @Override
    public String getCoupeCarTasksOrderByPrice() {
        return null;
    }
}
