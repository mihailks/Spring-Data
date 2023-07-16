package bg.softuni.exercisejsonprocessing.model.DTO.Q4;

import com.google.gson.annotations.Expose;

import java.util.List;

public class UserCountDTO {
    @Expose
    private int userCount;
    @Expose
    private List<UserAndProductsDTO> users;

    public UserCountDTO() {
    }

    public UserCountDTO(int userCount, List<UserAndProductsDTO> users) {
        this.userCount = userCount;
        this.users = users;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public List<UserAndProductsDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserAndProductsDTO> users) {
        this.users = users;
    }
}
