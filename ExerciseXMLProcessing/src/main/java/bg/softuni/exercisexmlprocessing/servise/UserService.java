package bg.softuni.exercisexmlprocessing.servise;

import bg.softuni.exercisexmlprocessing.model.DTO.UserSeedDTO;
import bg.softuni.exercisexmlprocessing.model.entity.User;

import java.util.List;

public interface UserService {
    long getEntityCount();

    void seedUsers(List<UserSeedDTO> users);

    User getRandomUser();
}
