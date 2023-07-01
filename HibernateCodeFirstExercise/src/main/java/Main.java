import Entities.Product;
import Entities.Sale;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

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




        entityManager.getTransaction().commit();


    }
}
