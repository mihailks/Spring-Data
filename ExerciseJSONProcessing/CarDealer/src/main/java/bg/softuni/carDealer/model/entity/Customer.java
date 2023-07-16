package bg.softuni.carDealer.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer extends BaseEntity {
    private String name;
    private LocalDate birthDate;
    private Boolean isYoungDriver;
    private List<Car> cars;

    public Customer() {
    }

    public Customer(String name, LocalDate localDate, Boolean isYoungDriver, List<Car> cars) {
        this.name = name;
        this.birthDate = localDate;
        this.isYoungDriver = isYoungDriver;
        this.cars = cars;
    }
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "birth_date")
    public LocalDate getLocalDate() {
        return birthDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.birthDate = localDate;
    }
    @Column(name = "is_young_driver")
    public Boolean getYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(Boolean youngDriver) {
        isYoungDriver = youngDriver;
    }
    @OneToMany
    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
