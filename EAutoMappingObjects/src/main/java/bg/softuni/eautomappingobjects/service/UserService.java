package bg.softuni.eautomappingobjects.service;

import bg.softuni.eautomappingobjects.model.dto.UserLogInDTO;
import bg.softuni.eautomappingobjects.model.dto.UserRegisterDTO;

public interface UserService {
    void registerUser(UserRegisterDTO userRegisterDTO);

    void logInUser(UserLogInDTO userLogInDTO);

    void logOutUser();

    boolean hasLoggedInUser();

    boolean loggedInUserIsAdmin();

    void viewOwnedGames();

    void purchaseGame(String title);
}
