package bg.softuni.eautomappingobjects.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class UserLogInDTO {
    private String email;
    private String password;

    public UserLogInDTO() {
    }

    public UserLogInDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Pattern(regexp = "[a-zA-z\\d]{6,}",
            message = "enter valid password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
