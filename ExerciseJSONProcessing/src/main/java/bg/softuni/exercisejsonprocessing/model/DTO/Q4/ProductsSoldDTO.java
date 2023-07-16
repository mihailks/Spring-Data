package bg.softuni.exercisejsonprocessing.model.DTO.Q4;

import bg.softuni.exercisejsonprocessing.model.entity.Product;
import com.google.gson.annotations.Expose;

import java.util.List;

public class ProductsSoldDTO {
    @Expose
    private int count;
    @Expose
    private List<Product> products;

    public ProductsSoldDTO() {
    }

    public ProductsSoldDTO(int count, List<Product> products) {
        this.count = count;
        this.products = products;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
