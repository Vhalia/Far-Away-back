package be.d2l.controller;


import be.d2l.customExceptions.ProductNotFoundException;
import be.d2l.model.Product;
import be.d2l.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/suits")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping()
    public Iterable<Product> getProducts(@RequestParam(required = false) String category,
                                         @RequestParam(required = false, defaultValue = "0")float min,
                                         @RequestParam(required = false, defaultValue = "0") float max,
                                         @RequestParam(required = false) String order){
        if(order != null && order.equals("ASC")){
            if(category != null){
                if(max != 0){
                    //liste filtrée pour une categorie et par prix
                    return service.findWithFilterModeAsc(category, min, max);
                }
                //liste filtrée par categorie
                return service.findByCategoryAsc(category);
            }
            if(max != 0) {
                //liste filtrée par prix
                return service.findWithPriceFilterModeAsc(min, max);
            }
            //liste triée asc
            return service.findAllAsc();
        }
        if(order != null && order.equals("DESC")){
            if(category != null){
                if(max != 0){
                    //liste filtrée pour une categorie et par prix
                    return service.findWithFilterModeDesc(category, min, max);
                }
                //liste filtrée par categorie
                return service.findByCategoryDesc(category);
            }
            if(max != 0) {
                //liste filtrée par prix
                return service.findWithPriceFilterModeDesc(min, max);
            }
            //liste triée desc
            return service.findAllDesc();
        }
        if(category != null){
            if(max != 0){
                //liste filtrée pour une categorie et par prix
                return service.findWithFilterMode(category, min, max);
            }
            //liste filtrée par categorie
            return service.findByCategory(category);
        }
        if(max != 0) {
            //liste filtrée par prix
            return service.findWithPriceFilterMode(min, max);
        }
        //liste sans filtre
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") int id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        if (product == null) return ResponseEntity.noContent().build();
        if (product.getId() < 0) return ResponseEntity.badRequest().build();
        Product savedProduct = service.saveProduct(product);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedProduct.getId()).toUri();
        return ResponseEntity.created(location).body(savedProduct);
    }

    @DeleteMapping("{/id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") int id) {
        if(id < 0) return ResponseEntity.badRequest().build();
        try{
            service.deleteProduct(id);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("{/id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") int id, @RequestBody Product product) {
        if (product == null) return ResponseEntity.noContent().build();
        if (id < 0)  return ResponseEntity.badRequest().build();
        try {
            return ResponseEntity.ok(service.updateProduct(id, product));
        }catch(ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }


}
