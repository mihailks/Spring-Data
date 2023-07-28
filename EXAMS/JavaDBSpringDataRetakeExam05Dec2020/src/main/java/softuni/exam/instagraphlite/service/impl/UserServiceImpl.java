package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.UserSeedDTO;
import softuni.exam.instagraphlite.models.entity.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.UserService;
import softuni.exam.instagraphlite.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    public static final String USERS_FILE_PATH = "src/main/resources/files/users.json";
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;
    private Gson gson;
    private PictureRepository pictureRepository;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson, PictureRepository pictureRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public boolean areImported() {
        return userRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(USERS_FILE_PATH));
    }

    @Override
    public String importUsers() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        UserSeedDTO[] test = gson.fromJson(readFromFileContent(), UserSeedDTO[].class);


        Arrays.stream(gson.fromJson(readFromFileContent(), UserSeedDTO[].class))
                .filter(userSeedDTO -> {
                    boolean isValid = validationUtil.isValid(userSeedDTO) &&
                            userRepository.findFirstByUsername(userSeedDTO.getUsername()).isEmpty()
                            && pictureRepository.findFirstByPath(userSeedDTO.getProfilePicture()).isPresent();

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported User: %s",
                                    userSeedDTO.getUsername())
                                    : "Invalid user")
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(userSeedDTO -> {
                    User user = modelMapper.map(userSeedDTO, User.class);
                    user.setPicture(pictureRepository.findFirstByPath(userSeedDTO.getProfilePicture()).orElse(null));
                    return user;
                })
                .forEach(userRepository::save);


        return stringBuilder.toString().trim();
    }

    @Override
    public String exportUsersWithTheirPosts() {
        StringBuilder stringBuilder = new StringBuilder();

        List<User> users = userRepository.findAllByPosts();

        for (User user : users) {

            stringBuilder.append(String.format(
                            "User: %s%n" +
                            "Post count: %d%n",
                            user.getUsername(),user.getPosts().size()));

            user.getPosts()
                    .stream()
                    .sorted(Comparator.comparingDouble(l -> l.getPicture().getSize()))
                    .forEach(post -> {
                stringBuilder.append(String.format(
                                "==Post Details:%n" +
                                "----Caption: %s%n" +
                                "----Picture Size: %.2f%n",
                        post.getCaption(),
                        post.getPicture().getSize()));
            });


        }

        return stringBuilder.toString().trim();
    }
}
