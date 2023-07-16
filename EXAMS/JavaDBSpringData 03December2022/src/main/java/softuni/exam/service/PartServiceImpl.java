package softuni.exam.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class PartServiceImpl implements PartService {
    @Override
    public boolean areImported() {
        return false;
    }

    @Override
    public String readPartsFileContent() throws IOException {
        return null;
    }

    @Override
    public String importParts() throws IOException {
        return null;
    }
}
