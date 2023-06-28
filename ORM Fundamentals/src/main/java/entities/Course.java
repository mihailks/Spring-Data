package entities;

import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Id;

@Entity(name = "courses")
public class Course {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "length_in_weeks")
    private int weeksLength;

    public Course() {
    }
    public Course(String name, int weeksLength) {
        this.name = name;
        this.weeksLength = weeksLength;
    }
    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weeksLength=" + weeksLength +
                '}';
    }
}
