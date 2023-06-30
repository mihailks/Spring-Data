import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Engine implements Runnable {

    private final EntityManager entityManager;
    private final BufferedReader bufferedReader;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        System.out.println("Select exercise number:");

        try {
            int exNumber = Integer.parseInt(bufferedReader.readLine());

            switch (exNumber) {
                case 2 -> exTwo();
                case 3 -> exThree();
                case 4 -> exFour();
                case 5 -> exFive();
                case 6 -> exSix();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    private void exTwo() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("update Town t set t.name = upper(t.name) " +
                "where length(t.name)<=5 ");
        int affectedRows = query.executeUpdate();

        System.out.println(affectedRows);

        entityManager.getTransaction().commit();
    }

    private void exThree() throws IOException {
        System.out.println("Enter employee full name");
        String[] fullName = bufferedReader.readLine().split("\\s+");
        String firstName = fullName[0];
        String lastName = fullName[1];

        Long singleResult = entityManager
                .createQuery("select count(e) from Employee e " +
                        "where e.firstName = :f_name and e.lastName = :l_name", Long.class)
                .setParameter("f_name", firstName)
                .setParameter("l_name", lastName)
                .getSingleResult();

        if (singleResult == 1) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }

    public void exFour() {

        entityManager.createQuery("select e from Employee e " +
                        "where e.salary>50000", Employee.class)
                .getResultList()
                .forEach(e -> System.out.println(e.getFirstName()));

//            entityManager.createQuery("select e from Employee e " +
//                            "where e.salary>50000", Employee.class)
//                    .getResultStream()
//                    .map(Employee::getFirstName)
//                    .forEach(System.out::println);

//        employeeList.forEach(e->System.out.println(e.getFirstName()));


//        List<Employee> employeeListTest = entityManager.createQuery("select e from Employee e " +
//                "where e.salary>:min_salary", Employee.class)
//                .setParameter("min_salary", BigDecimal.valueOf(50000L))
//                .getResultList();
    }

    public void exFive() {
        List<Employee> employeesByDepartment = entityManager.createQuery(
                        "select e from Employee e " +
                                "where e.department.name=:dp_name " +
                                "order by e.salary asc, e.id asc", Employee.class)
                .setParameter("dp_name", "Research and Development")
                .getResultList();

        employeesByDepartment.forEach(e -> {
            System.out.printf("%s %s from %s - $%s%n", e.getFirstName(), e.getLastName(), e.getDepartment().getName(), e.getSalary());
        });
    }

    public void exSix() {
        System.out.println("Enter last name");
        Scanner scanner = new Scanner(System.in);
        String lastName = scanner.nextLine();

        Employee employee = entityManager.createQuery("SELECT e" +
                        " FROM Employee e" +
                        " WHERE e.lastName = :l_name", Employee.class)
                .setParameter("l_name", lastName)
                .getSingleResult();

        System.out.println(employee.getAddress().getText());

    }

}
