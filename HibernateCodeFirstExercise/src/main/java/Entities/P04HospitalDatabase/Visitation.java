package Entities.P04HospitalDatabase;

import Entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
@Entity
@Table(name = "visitations")
public class Visitation extends BaseEntity {
    private LocalDate date;
    private String comment;

    public Visitation(){}


    @Column(name = "date")
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    @Column(name = "comment", columnDefinition = "TEXT")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
