package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PartSeedDTO;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.PartsRepository;
import softuni.exam.service.PartsService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class PartsServiceImpl implements PartsService {
    public static final String PART_FILE_PATH = "src/main/resources/files/json/parts.json";
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final PartsRepository partsRepository;

    public PartsServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson, PartsRepository partsRepository) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.partsRepository = partsRepository;
    }

    @Override
    public boolean areImported() {
        return partsRepository.count() > 0;
    }

    @Override
    public String readPartsFileContent() throws IOException {
        return Files.readString(Path.of(PART_FILE_PATH));
    }

    @Override
    public String importParts() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        PartSeedDTO[] partSeedDTOs = gson.fromJson(readPartsFileContent(), PartSeedDTO[].class);

        for (PartSeedDTO partSeedDTO : partSeedDTOs) {

            stringBuilder.append(System.lineSeparator());

            Optional<Part> partOptional = partsRepository.findFirstByPartName(partSeedDTO.getPartName());

            if (!validationUtil.isValid(partSeedDTO) || partOptional.isPresent()) {
                stringBuilder.append("Invalid part");
                continue;
            }

            Part part = modelMapper.map(partSeedDTO, Part.class);
            partsRepository.save(part);

            stringBuilder.append(String.format("Successfully imported part %s - %s",
                    partSeedDTO.getPartName(), partSeedDTO.getPrice()));
        }
        return stringBuilder.toString().trim();
    }

    @Override
    public Part findByID(Long id) {
        return partsRepository.findById(id).orElse(null);
    }
}

