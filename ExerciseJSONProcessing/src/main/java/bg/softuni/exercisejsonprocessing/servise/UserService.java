package bg.softuni.exercisejsonprocessing.servise;

import bg.softuni.exercisejsonprocessing.model.entity.User;

import java.io.IOException;

public interface UserService {
    void seedUsers() throws IOException;

    User findRandomUser();
}
