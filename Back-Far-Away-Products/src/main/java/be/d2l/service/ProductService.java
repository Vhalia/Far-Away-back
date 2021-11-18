package be.d2l.service;

import be.d2l.model.Product;
import be.d2l.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    public Iterable<Product> findAll() {
        return repo.findAll();
    }

    /**
     * filter products by the price
     * @param min price minimum
     * @param max price maximum
     * @return a list of products
     */
    public Iterable<Product> findWithPriceFilterMode(float min, float max){
        return repo.findByPriceBetween(min, max);
    }

    /**
     * filter products by the price and the category
     * @param category the category of a product
     * @param min price minimum
     * @param max price maximum
     * @return a list of products
     */
    public Iterable<Product> findWithFilterMode(String category, float min, float max){
        return repo.findByCategoryAndPriceBetween(category, min, max);
    }

    /**
     * filter products by the category
     * @param category the category of a product
     * @return a list of products
     */
    public Iterable<Product> findByCategory(String category) {
        return repo.findByCategory(category);
    }

    public Product findById(int id) {
        return repo.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "No object with this id: " + id));
    }

    public Product saveProduct(Product product) {
        return repo.save(product);
    }

    public Product updateProduct(int id, Product product) {
        Product productGet = repo.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "No object with this id: " + id));
        productGet.setName(product.getName());
        productGet.setPrice(product.getPrice());
        productGet.setCategory(product.getCategory());
        return repo.save(productGet);
    }

    public void deleteProduct(int id) {
        repo.deleteById(id);
    }


}
