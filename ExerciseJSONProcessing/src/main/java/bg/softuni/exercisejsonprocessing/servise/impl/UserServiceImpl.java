package bg.softuni.exercisejsonprocessing.servise.impl;

import bg.softuni.exercisejsonprocessing.constanst.GlobalConstants;
import bg.softuni.exercisejsonprocessing.model.DTO.CategorySeedDTO;
import bg.softuni.exercisejsonprocessing.model.DTO.UserSeedDTO;
import bg.softuni.exercisejsonprocessing.model.entity.Category;
import bg.softuni.exercisejsonprocessing.model.entity.User;
import bg.softuni.exercisejsonprocessing.repository.UserRepository;
import bg.softuni.exercisejsonprocessing.servise.UserService;
import bg.softuni.exercisejsonprocessing.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static bg.softuni.exercisejsonprocessing.constanst.GlobalConstants.RESOURCES_FILE_PATH;

@Service
public class UserServiceImpl implements UserService {
    public static final String USERS_FILE_NAME = "users.json";
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public void seedUsers() throws IOException {
        if (userRepository.count() > 0) {
            return;
        }
//        String fileContent = Files
//                .readString(Path.of(RESOURCES_FILE_PATH + USERS_FILE_NAME));
//
//        UserSeedDTO[] userSeedDTOs = gson.fromJson(fileContent, UserSeedDTO[].class);
//
//        Arrays.stream(userSeedDTOs)
//                .filter(validationUtil::isValid)
//                .map(userSeedDTO -> modelMapper.map(userSeedDTO, User.class))
//                .forEach(userRepository::save);

        Arrays.stream(gson.fromJson(
                        Files.readString(Path.of(RESOURCES_FILE_PATH + USERS_FILE_NAME)),
                        UserSeedDTO[].class))
                .filter(validationUtil::isValid)
                .map(userSeedDTO -> modelMapper.map(userSeedDTO, User.class))
                .forEach(userRepository::save);

    }

    @Override
    public User findRandomUser() {
        long randomId = ThreadLocalRandom
                .current().nextLong(1, userRepository.count() + 1);
        return userRepository.findById(randomId)
                .orElse(null);
    }
}
