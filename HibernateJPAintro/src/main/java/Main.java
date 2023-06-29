import entities.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("school");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Student student = new Student("TeoNew",10);
        em.persist(student);

        student.setName("Mariya");
        em.persist(student);

        Student studentToRemove = em.find(Student.class, 1);
        em.remove(studentToRemove);


        List<Student> fromStudentNew = em
                .createQuery("FROM Student", Student.class)
                .getResultList();
        fromStudentNew.forEach(System.out::println);


        em.getTransaction().commit();
    }
}
