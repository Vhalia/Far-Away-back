package be.d2l.service;

import be.d2l.customExceptions.ProductNotFoundException;
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

    public Iterable<Product> findAllAsc() {
        return repo.findAllByOrderByPriceAsc();
    }

    public Iterable<Product> findAllDesc() {
        return repo.findAllByOrderByPriceDesc();
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

    /**
     * filter products by the price Order by it ASC
     * @param min price minimum
     * @param max price maximum
     * @return a list of products
     */
    public Iterable<Product> findWithPriceFilterModeAsc(float min, float max){
        return repo.findByPriceBetweenOrderByPriceAsc(min, max);
    }

    /**
     * filter products by the price and the category Order by price ASC
     * @param category the category of a product
     * @param min price minimum
     * @param max price maximum
     * @return a list of products
     */
    public Iterable<Product> findWithFilterModeAsc(String category, float min, float max){
        return repo.findByCategoryAndPriceBetweenOrderByPriceAsc(category, min, max);
    }

    /**
     * filter products by the category order by price ASC
     * @param category the category of a product
     * @return a list of products
     */
    public Iterable<Product> findByCategoryAsc(String category) {
        return repo.findByCategoryOrderByPriceAsc(category);
    }

    /**
     * filter products by the price Order by it DESC
     * @param min price minimum
     * @param max price maximum
     * @return a list of products
     */
    public Iterable<Product> findWithPriceFilterModeDesc(float min, float max){
        return repo.findByPriceBetweenOrderByPriceDesc(min, max);
    }

    /**
     * filter products by the price and the category Order by price DESC
     * @param category the category of a product
     * @param min price minimum
     * @param max price maximum
     * @return a list of products
     */
    public Iterable<Product> findWithFilterModeDesc(String category, float min, float max){
        return repo.findByCategoryAndPriceBetweenOrderByPriceDesc(category, min, max);
    }

    /**
     * filter products by the category order by price Order by price DESC
     * @param category the category of a product
     * @return a list of products
     */
    public Iterable<Product> findByCategoryDesc(String category) {
        return repo.findByCategoryOrderByPriceDesc(category);
    }

    public Product findById(int id) throws ProductNotFoundException {
        return repo.findById(id).orElseThrow(()-> new ProductNotFoundException("this product doesn't exist " + id));
    }

    public Product saveProduct(Product product) {
        return repo.save(product);
    }

    public Product updateProduct(int id, Product product) throws ProductNotFoundException {
        Product productGet = repo.findById(id).orElseThrow(()-> new ProductNotFoundException("this product doesn't exist " + id));
        if(product.getName() != null)
            productGet.setName(product.getName());
        if(product.getPrice() != null)
            productGet.setPrice(product.getPrice());
        if(product.getCategory() != null)
            productGet.setCategory(product.getCategory());
        if(product.getDetailedDescription() != null)
            productGet.setDetailedDescription(product.getDetailedDescription());
        if(product.getShortDescription() != null)
            productGet.setShortDescription(product.getShortDescription());
        return repo.save(productGet);
    }

    public void deleteProduct(int id) throws ProductNotFoundException {
        if(!repo.existsById(id)) throw new ProductNotFoundException("this product doesn't exist " + id);
        repo.deleteById(id);
    }


}
