package be.d2l.repo;

import be.d2l.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    List<Product> findByPriceBetween(float price1, float price2);

    List<Product> findByCategoryAndPriceBetween(String category, float price1, float price2);

    List<Product> findByCategory(String category);
}
