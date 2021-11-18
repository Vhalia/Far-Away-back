package be.d2l.controller;


import be.d2l.model.Product;
import be.d2l.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/suits")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping()
    public Iterable<Product> getProducts(@RequestParam(required = false) String category, @RequestParam(required = false, defaultValue = "0")float min, @RequestParam(required = false, defaultValue = "0") float max){
        if(category != null){
            if(max != 0){
                //liste filtrée pour une categorie et par prix
                return service.findWithFilterMode(category, min, max);
            }
            //liste filtrée par categorie
            return service.findByCategory(category);
        }
        if(max != 0){
            //liste filtrée par prix
            return service.findWithPriceFilterMode(min, max);
        }
        //liste sans filtre
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") int id) { return service.findById(id);}

    @PostMapping
    public void createProduct(@RequestBody Product product) { service.saveProduct(product);}

    @DeleteMapping("{/id}")
    public void deleteProduct(@PathVariable("id") int id) { service.deleteProduct(id);}

    @PutMapping("{/id}")
    public void updateProduct(@PathVariable("id") int id, @RequestBody Product product) { service.updateProduct(id, product); }


}
