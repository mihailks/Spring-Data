package bg.softuni.eautomappingobjects.service;

import bg.softuni.eautomappingobjects.model.dto.UserLogInDTO;
import bg.softuni.eautomappingobjects.model.dto.UserRegisterDTO;

public interface UserService {
    void registerUser(UserRegisterDTO userRegisterDTO);

    void logInUser(UserLogInDTO userLogInDTO);

    void logOutUser();

}
