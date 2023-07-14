package bg.softuni.exercisejsonprocessing.servise;

import bg.softuni.exercisejsonprocessing.model.DTO.UserSoldDTO;
import bg.softuni.exercisejsonprocessing.model.entity.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    void seedUsers() throws IOException;

    User findRandomUser();


    List<UserSoldDTO> findAllUsersWithMoreThenProductsSold();
}
