package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dto.PicturesSeedRootDTO;
import softuni.exam.domain.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.PictureService;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PictureServiceImpl implements PictureService {
    public static final String PICTURES_FILE_PATH = "src/main/resources/files/xml/pictures.xml";
    private PictureRepository pictureRepository;
    private ModelMapper modelMapper;
    private ValidatorUtil validatorUtil;
    private XmlParser xmlParser;

    public PictureServiceImpl(PictureRepository pictureRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil, XmlParser xmlParser) {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public String importPictures() throws IOException {
        return Files.readString(Path.of(PICTURES_FILE_PATH));
    }

    @Override
    public boolean areImported() {
        return pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesXmlFile() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        xmlParser
                .parseXml(PicturesSeedRootDTO.class, PICTURES_FILE_PATH)
                .getPictures()
                .stream()
                .filter(pictureSeedDto -> {
                    boolean isValid = validatorUtil.isValid(pictureSeedDto);

                    if (pictureSeedDto.getUrl() == null) {
                        isValid = false;
                    }

                    sb.append(isValid
                            ?
                            String.format("Successfully imported picture - %s",
                                    pictureSeedDto.getUrl())
                            :
                            "Invalid Picture"
                    ).append(System.lineSeparator());
                    return isValid;
                })
                .map(pictureSeedDto -> modelMapper.map(pictureSeedDto, Picture.class))
                .forEach(this.pictureRepository::save);
        return sb.toString();
    }

}
