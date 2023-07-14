package bg.softuni.exercisexmlprocessing.servise.impl;

import bg.softuni.exercisexmlprocessing.model.DTO.UserSeedDTO;
import bg.softuni.exercisexmlprocessing.model.entity.User;
import bg.softuni.exercisexmlprocessing.repository.UserRepository;
import bg.softuni.exercisexmlprocessing.servise.UserService;
import bg.softuni.exercisexmlprocessing.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public long getEntityCount() {
        return userRepository.count();
    }

    @Override
    public void seedUsers(List<UserSeedDTO> users) {
        users
                .stream()
                .filter(validationUtil::isValid)
                .map(userSeedDTO -> modelMapper.map(userSeedDTO, User.class))
                .forEach(userRepository::save);
    }

    @Override
    public User getRandomUser() {
        long randomId = ThreadLocalRandom.current().nextLong(1, userRepository.count() + 1);


        return userRepository.findById(randomId).orElse(null);
    }
}
