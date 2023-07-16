package bg.softuni.exercisejsonprocessing.model.DTO.Q4;

import com.google.gson.annotations.Expose;

import java.util.List;

public class UserAndProductsDTO {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private int age;
    @Expose
    private List<ProductsSoldDTO> soldProducts;

    public UserAndProductsDTO() {
    }

    public UserAndProductsDTO(String firstName, String lastName, int age, List<ProductsSoldDTO> soldProducts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.soldProducts = soldProducts;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<ProductsSoldDTO> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<ProductsSoldDTO> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
