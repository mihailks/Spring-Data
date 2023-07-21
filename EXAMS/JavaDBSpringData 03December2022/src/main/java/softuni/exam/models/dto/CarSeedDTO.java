package softuni.exam.models.dto;

import softuni.exam.models.entity.CarType;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CarSeedDTO {

    @XmlElement(name = "carMake")
    private String carMake;
    @XmlElement(name = "carModel")
    private String carModel;
    @XmlElement(name = "year")
    private Integer year;
    @XmlElement(name = "plateNumber")
    private String plateNumber;
    @XmlElement(name = "kilometers")
    private Integer kilometers;
    @XmlElement(name = "engine")
    private Double engine;
    @XmlElement(name = "carType")
    private CarType carType;

    public CarSeedDTO() {
    }

    @NotNull
    @Size(min = 2, max = 30)
    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    @NotNull
    @Size(min = 2, max = 30)
    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    @NotNull
    @Positive
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @NotNull
    @Size(min = 2, max = 30)
    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    @NotNull
    @Positive
    public Integer getKilometers() {
        return kilometers;
    }

    public void setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
    }

    @NotNull
    @DecimalMin("1.00")
    public Double getEngine() {
        return engine;
    }

    public void setEngine(Double engine) {
        this.engine = engine;
    }

    @NotNull
    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }
}
