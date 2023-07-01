import entities.Address;
import entities.Employee;
import entities.Project;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

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
                case 7 -> exSeven();
                case 8 -> exEight();
                case 9 -> exNine();
                case 10 -> exTen();
                case 11 -> exEleven();
                case 12 -> exTwelve();
                case 13 -> exThirteen();
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

        entityManager.createQuery("select t from Town t");
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

        employeesByDepartment.forEach(e -> System.out.printf("%s %s from %s - $%s%n",
                e.getFirstName(),
                e.getLastName(),
                e.getDepartment().getName(),
                e.getSalary()));
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


        Address address = createAddress("Vitoshka 15");

        entityManager.getTransaction().begin();
        employee.setAddress(address);
        entityManager.persist(employee);
        entityManager.getTransaction().commit();
    }

    private Address createAddress(String addressText) {
        Address address = new Address();
        address.setText(addressText);

        entityManager.getTransaction().begin();
        entityManager.persist(address);
        entityManager.getTransaction().commit();

        return address;
    }

    public void exSeven() {
        List<Address> addresses = entityManager.createQuery("select a from Address a " +
                        "order by a.employees.size desc", Address.class)
                .setMaxResults(10)
                .getResultList();

        addresses.forEach(a -> {
            System.out.printf("%s, %s - %d employees%n",
                    a.getText(),
                    a.getTown() == null
                            ? "unknown" : a.getTown().getName(),
                    a.getEmployees().size());
        });
    }

    public void exEight() throws IOException {
        System.out.println("Please enter employee ID");
        int id = Integer.parseInt(bufferedReader.readLine());
        Employee employee = entityManager.find(Employee.class, id);

        List<String> projects = employee.getProjects()
                .stream().map(Project::getName)
                .sorted()
                .collect(Collectors.toList());

        System.out.printf("%s %s - %s %s",
                employee.getFirstName(),
                employee.getLastName(),
                employee.getJobTitle(),
                String.join(", ", projects));
    }

    public void exNine() {

        List<Project> projects = entityManager.createQuery("select p from Project p " +
                        "order by p.startDate desc", Project.class)
                .setMaxResults(10)
                .getResultList();

        projects.stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(p -> System.out.printf("Project name: %s%n" +
                                "Project Description: %s%n" +
                                "Project Start Date: %s%n" +
                                "Project End Date: %s%n",
                        p.getName(), p.getDescription(), p.getStartDate(), p.getEndDate()));


    }

    public void exTen() {
        entityManager.getTransaction().begin();

        int rows = entityManager.createQuery("update Employee e " +
                        "set e.salary = e.salary*1.12 " +
                        "where e.department.id in :ids")
                .setParameter("ids", Set.of(1, 2, 4, 11))
                .executeUpdate();

        entityManager.getTransaction().commit();
        System.out.println(rows);
    }

    public void exEleven() throws IOException {
        System.out.println("Enter pattern");
        String pattern = bufferedReader.readLine();

        List<Employee> employees = entityManager.createQuery("select e from Employee e " +
                        "where e.firstName like :pattern", Employee.class)
                .setParameter("pattern", pattern.concat("%"))
                .getResultList();

        employees.forEach(e -> System.out.printf("%s %s - %s - ($%.2f)%n",
                e.getFirstName(), e.getLastName(), e.getJobTitle(), e.getSalary()));

    }

    public void exTwelve() {
        entityManager.createQuery("SELECT e.department.name, MAX(e.salary) FROM Employee e " +
                        "GROUP BY e.department.id " +
                        "HAVING MAX(e.salary) NOT BETWEEN 30000 AND 70000", Object[].class)
                .getResultList()
                .forEach(e -> System.out.println(e[0] + "  " + e[1]));
    }

    public void exThirteen() throws IOException {
        System.out.println("Enter town name");
        String townName = bufferedReader.readLine();

        entityManager.getTransaction().begin();
        final Town townDelete = entityManager.createQuery("FROM Town "
                        + "WHERE name = :town", Town.class)
                .setParameter("town", townName)
                .getSingleResult();

        final List<Address> addressesToDelete = entityManager
                .createQuery("FROM Address WHERE town.id= :id", Address.class)
                .setParameter("id", townDelete.getId())
                .getResultList();

        addressesToDelete
                .forEach(t -> t.getEmployees()
                        .forEach(em -> em.setAddress(null)));

        addressesToDelete.forEach(entityManager::remove);
        entityManager.remove(townDelete);

        final int countDeletedAddresses = addressesToDelete.size();

        entityManager.getTransaction().commit();

        System.out.printf("%d address%s in %s deleted",
                countDeletedAddresses,
                countDeletedAddresses == 1 ? "" : "es",
                townName);
    }
}