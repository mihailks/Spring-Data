package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.PictureSeedDTO;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {
    public static final String PICTURES_FILE_PATH = "src/main/resources/files/pictures.json";
    private PictureRepository pictureRepository;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;
    private Gson gson;

    public PictureServiceImpl(PictureRepository pictureRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }


    @Override
    public boolean areImported() {
        return pictureRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(PICTURES_FILE_PATH));
    }

    @Override
    public String importPictures() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(gson.fromJson(readFromFileContent(), PictureSeedDTO[].class))
                .filter(pictureSeedDTO -> {
                    boolean isValid = validationUtil.isValid(pictureSeedDTO);
                    if (pictureRepository.findFirstByPath(pictureSeedDTO.getPath()).isPresent()) {
                        isValid = false;
                    }

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported Picture, with size %.2f",
                                    pictureSeedDTO.getSize())
                                    : "Invalid size")
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(pictureSeedDTO -> modelMapper.map(pictureSeedDTO, Picture.class))
                .forEach(pictureRepository::save);


        return stringBuilder.toString().trim();
    }

    @Override
    public String exportPictures() {
        StringBuilder stringBuilder = new StringBuilder();

        List<Picture> pictures = pictureRepository.findAllBySizeGreaterThanOrderBySize(30000.0);

        for (Picture picture : pictures) {
            stringBuilder
                    .append(String.format("%.2f – %s%n", picture.getSize(), picture.getPath()));
        }

        return stringBuilder.toString().trim();
    }
}
