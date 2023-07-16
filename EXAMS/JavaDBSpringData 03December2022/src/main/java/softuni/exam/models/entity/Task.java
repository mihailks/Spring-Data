package softuni.exam.models.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task extends BaseEntity{
    private BigDecimal price;
    private LocalDate date;

//    private Mechanic mechanic;
//    private Part part;
//    private Car car;

    public Task() {
    }

    public Task(BigDecimal price, LocalDate date) {
        this.price = price;
        this.date = date;

    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


}
