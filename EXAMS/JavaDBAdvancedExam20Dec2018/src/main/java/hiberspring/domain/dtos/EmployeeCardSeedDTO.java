package hiberspring.domain.dtos;

import com.google.gson.annotations.Expose;

public class EmployeeCardSeedDTO {
    @Expose
    private String number;

    public EmployeeCardSeedDTO() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
