package com.example.football.service.impl;

import com.example.football.models.dto.stats.StatsSeedRootDTO;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import com.example.football.util.ValidationUtil;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class StatServiceImpl implements StatService {
    public static final String STATS_FILE_PATH = "src/main/resources/files/xml/stats.xml";
    private StatRepository statRepository;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;
    private XmlParser xmlParser;

    public StatServiceImpl(StatRepository statRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.statRepository = statRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }


    @Override
    public boolean areImported() {
        return statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(Path.of(STATS_FILE_PATH));
    }

    @Override
    public String importStats() throws JAXBException, FileNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();


        xmlParser.fromFile(STATS_FILE_PATH, StatsSeedRootDTO.class)
                .getStats()
                .stream()
                .filter(statsSeedDTO -> {
                    boolean isValid = validationUtil.isValid(statsSeedDTO);

                    Optional<Stat> statValues = statRepository
                            .findFirstByEnduranceAndPassingAndShooting(
                                    statsSeedDTO.getEndurance(),
                                    statsSeedDTO.getPassing(),
                                    statsSeedDTO.getShooting());

                    if (statValues.isPresent()) {
                        isValid = false;
                    }

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported Stat %.2f - %.2f - %.2f",
                                    statsSeedDTO.getShooting(), statsSeedDTO.getPassing(), statsSeedDTO.getEndurance())
                                    : "Invalid Stat")
                            .append(System.lineSeparator());


                    return isValid;
                })
                .map(statsSeedDTO -> modelMapper.map(statsSeedDTO, Stat.class))
                .forEach(statRepository::save);


        return stringBuilder.toString().trim();
    }
}
