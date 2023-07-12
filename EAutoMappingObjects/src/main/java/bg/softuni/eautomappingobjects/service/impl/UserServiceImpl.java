package bg.softuni.eautomappingobjects.service.impl;

import bg.softuni.eautomappingobjects.model.dto.UserLogInDTO;
import bg.softuni.eautomappingobjects.model.dto.UserRegisterDTO;
import bg.softuni.eautomappingobjects.model.entity.User;
import bg.softuni.eautomappingobjects.repository.UserRepository;
import bg.softuni.eautomappingobjects.service.UserService;
import bg.softuni.eautomappingobjects.util.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private User loggedInUser;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }


    @Override
    public void registerUser(UserRegisterDTO userRegisterDTO) {
        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
            System.out.println("Wrong confirm password");
        }

        Set<ConstraintViolation<UserRegisterDTO>> violations = validationUtil.violation(userRegisterDTO);

        if (!violations.isEmpty()) {
            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return;
        }

        User user = modelMapper.map(userRegisterDTO, User.class);

        Optional<User> firstUserToAdmin = userRepository.findById(1L);
        if (firstUserToAdmin.isEmpty()){
            user.setIsAdmin(true);
        } else {
            user.setIsAdmin(false);
        }
        userRepository.save(user);
    }

    @Override
    public void logInUser(UserLogInDTO userLogInDTO) {
        Set<ConstraintViolation<UserLogInDTO>> violations = validationUtil.violation(userLogInDTO);

        if (!violations.isEmpty()) {
            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return;
        }

        User user = userRepository.findByEmailAndPassword(userLogInDTO.getEmail(), userLogInDTO.getPassword())
                .orElse(null);

        if (user == null) {
            System.out.println("Incorrect username / password");
            return;
        }

        loggedInUser = user;

    }

    @Override
    public void logOutUser() {
        if (loggedInUser == null) {
            System.out.println("Cannot log out. No user was logged in.");
        } else {
            loggedInUser = null;
        }

    }
}
