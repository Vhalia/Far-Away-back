package be.d2l.controller;


import be.d2l.customExceptions.BasketNotFoundException;
import be.d2l.model.Basket;
import be.d2l.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.net.URI;

@RestController
@RequestMapping("/baskets")
public class BasketController {

    @Autowired
    private BasketService service;

    @GetMapping("/{idUser}")
    public ResponseEntity<Iterable<Basket>> getBasketOfUser(@PathVariable("idUser") int idUser){
        if (idUser < 0) return ResponseEntity.badRequest().build();
        Iterable<Basket> baskets = service.findBasketOfUser(idUser);
        return ResponseEntity.ok(baskets);
    }
    @DeleteMapping("/{idProduct}/{idUser}")
    public void deleteProductOfBasket(@PathVariable("idProduct") int idProduct, @PathVariable("idUser") int idUser){
        service.deleteProductOfBasket(idProduct,idUser);
    }

    @PutMapping("/{idProduct}/{idUser}")
    public Iterable<Basket> updateProductQuantity(@RequestBody int quantity, @PathVariable("idProduct") int idProduct, @PathVariable("idUser") int idUser){
        return service.updateProductQuantity(quantity,idProduct,idUser);
    }

    @PostMapping("/{idProduct}/{idUser}")
    public void addProductToBasket(@PathVariable("idProduct") int idProduct, @PathVariable("idUser") int idUser){
        service.addProductToBasket(idProduct,idUser);
    }
}
