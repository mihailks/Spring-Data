package softuni.exam.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class MechanicServiceImpl implements MechanicService {
    @Override
    public boolean areImported() {
        return false;
    }

    @Override
    public String readMechanicsFromFile() throws IOException {
        return null;
    }

    @Override
    public String importMechanics() throws IOException {
        return null;
    }
}
