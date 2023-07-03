import Entities.P04HospitalDatabase.Patient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManager entityManager = Persistence.createEntityManagerFactory("test")
                .createEntityManager();

        entityManager.getTransaction().begin();
//
//        Sale sale = new Sale();
//        sale.setDate(LocalDate.now());
//
//
//        Product product = new Product();
//        product.setName("testMe");
//        product.setPrice(BigDecimal.TEN);
//        product.setQuantity(5.0);
//
//        product.getSales().add(sale);
//        sale.setProduct(product);


        ///----4. Hospital Database

//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Enter your choice: ");
//        System.out.println("Press `1` to show information about a patient");
//        System.out.println("Press `2` to enter information about a patient");
//
//        int input = Integer.parseInt(scanner.nextLine());
//
//        System.out.println("Please enter first name:");
//        String firstName = scanner.nextLine();
//        System.out.println("Please enter last name:");
//        String lastName = scanner.nextLine();
//
//        if (input == 1) {
//            List<Patient> currentSelectionOfPatients = entityManager.createQuery("select p from Patient p " +
//                            "where p.firstName = :f_name and p.lastName = :l_name", Patient.class)
//                    .setParameter("f_name", firstName)
//                    .setParameter("l_name", lastName)
//                    .getResultList();
//
//            //TODO show IDs and info and ask again
//        } else {
//            Patient patient = new Patient(firstName, lastName);
//            entityManager.persist(patient);
//            //TODO add other props for the patient
//        }

//        6. *Football Betting Database
        //TODO fix some things...


        entityManager.getTransaction().commit();
    }
}
