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

    List<Product> findByPriceBetweenOrderByPriceAsc(float price1, float price2);

    List<Product> findByCategoryAndPriceBetweenOrderByPriceAsc(String category, float price1, float price2);

    List<Product> findByCategoryOrderByPriceAsc(String category);

    List<Product> findByPriceBetweenOrderByPriceDesc(float price1, float price2);

    List<Product> findByCategoryAndPriceBetweenOrderByPriceDesc(String category, float price1, float price2);

    List<Product> findByCategoryOrderByPriceDesc(String category);

    List<Product> findAllByOrderByPriceAsc();

    List<Product> findAllByOrderByPriceDesc();


}
