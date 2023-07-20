package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.DTO.PictureSeedDTO;
import softuni.exam.models.entity.Picture;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.PictureService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PictureServiceImpl implements PictureService {
    private static final String PICS_FILE_PATH = "src\\main\\resources\\files\\json\\pictures.json";
    private PictureRepository pictureRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final CarRepository carRepository;

    public PictureServiceImpl(PictureRepository pictureRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil,  CarRepository carRepository) {
        this.pictureRepository = pictureRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.carRepository = carRepository;
    }

    @Override
    public boolean areImported() {
        return pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {

        return Files.readString(Path.of(PICS_FILE_PATH));
    }

    @Override
    public String importPictures() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        PictureSeedDTO[] pictureSeedDTOs = gson
                .fromJson(readPicturesFromFile(), PictureSeedDTO[].class);

        for (PictureSeedDTO pictureSeedDTO : pictureSeedDTOs) {
            stringBuilder.append(System.lineSeparator());

            if (validationUtil.isValid(pictureSeedDTO)) {

                Picture picture = modelMapper.map(pictureSeedDTO, Picture.class);
                picture.setCar(carRepository.findById(pictureSeedDTO.getCar()).orElse(null));
                pictureRepository.save(picture);

                stringBuilder.append(String.format("Successfully import picture - %s", pictureSeedDTO.getName()));
            } else {
                stringBuilder.append("Invalid picture");
            }
        }
        return stringBuilder.toString().trim();
    }
}
