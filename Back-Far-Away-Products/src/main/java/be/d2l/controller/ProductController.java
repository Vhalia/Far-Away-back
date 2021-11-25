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
    public ResponseEntity<Iterable<Product>> getProducts(@RequestParam(required = false) String category,
                                         @RequestParam(required = false, defaultValue = "0")float min,
                                         @RequestParam(required = false, defaultValue = "0") float max,
                                         @RequestParam(required = false) String order){
        if((order != null && (!order.equals("ASC") || !order.equals("DESC"))) || min < 0 || max < 0 || max < min){
            return ResponseEntity.badRequest().build();
        }
        if(order != null && order.equals("ASC")){
            if(category != null){
                if(max != 0){
                    //liste filtrée pour une categorie et par prix
                    return ResponseEntity.ok(service.findWithFilterModeAsc(category, min, max));
                }
                //liste filtrée par categorie
                return ResponseEntity.ok(service.findByCategoryAsc(category));
            }
            if(max != 0) {
                //liste filtrée par prix
                return ResponseEntity.ok(service.findWithPriceFilterModeAsc(min, max));
            }
            //liste triée asc
            return ResponseEntity.ok(service.findAllAsc());
        }
        if(order != null && order.equals("DESC")){
            if(category != null){
                if(max != 0){
                    //liste filtrée pour une categorie et par prix
                    return ResponseEntity.ok(service.findWithFilterModeDesc(category, min, max));
                }
                //liste filtrée par categorie
                return ResponseEntity.ok(service.findByCategoryDesc(category));
            }
            if(max != 0) {
                //liste filtrée par prix
                return ResponseEntity.ok(service.findWithPriceFilterModeDesc(min, max));
            }
            //liste triée desc
            return ResponseEntity.ok(service.findAllDesc());
        }
        if(category != null){
            if(max != 0){
                //liste filtrée pour une categorie et par prix
                return ResponseEntity.ok(service.findWithFilterMode(category, min, max));
            }
            //liste filtrée par categorie
            return ResponseEntity.ok(service.findByCategory(category));
        }
        if(max != 0) {
            //liste filtrée par prix
            return ResponseEntity.ok(service.findWithPriceFilterMode(min, max));
        }
        //liste sans filtre
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") int id) {
        if(id < 0) return ResponseEntity.badRequest().build();
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        if (product == null) return ResponseEntity.noContent().build();
        Product savedProduct = service.saveProduct(product);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedProduct.getId()).toUri();
        return ResponseEntity.created(location).body(savedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") int id) {
        if(id < 0) return ResponseEntity.badRequest().build();
        try{
            service.deleteProduct(id);
        }catch (ProductNotFoundException e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") int id, @RequestBody Product product) {
        if (product == null) return ResponseEntity.badRequest().build();
        if (id < 0)  return ResponseEntity.badRequest().build();
        try {
            return ResponseEntity.ok(service.updateProduct(id, product));
        }catch(ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }


}
