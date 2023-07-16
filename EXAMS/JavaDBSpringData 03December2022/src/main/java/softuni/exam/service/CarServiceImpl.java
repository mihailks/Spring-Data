package softuni.exam.service;

import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
@Service
public class CarServiceImpl implements CarService {
    @Override
    public boolean areImported() {
        return false;
    }

    @Override
    public String readCarsFromFile() throws IOException {
        return null;
    }

    @Override
    public String importCars() throws IOException, JAXBException {
        return null;
    }
}
