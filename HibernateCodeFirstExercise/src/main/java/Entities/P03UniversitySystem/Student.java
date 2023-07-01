package Entities.P03UniversitySystem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student extends User{
    private Float averageGrade;
    private Integer attendance;
    private Set<Course> courses;

    public Student() {

    }

    public Student(String firstName, String lastName, String phoneNumber, Set<Course> courses) {
        super(firstName, lastName, phoneNumber);
        this.courses = new HashSet<>();
    }

    @Column(name = "average_grade")
    public Float getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(Float averageGrade) {
        this.averageGrade = averageGrade;
    }
@Column(name = "attendance")
    public Integer getAttendance() {
        return attendance;
    }

    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }

    @ManyToMany(mappedBy = "students")
    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
