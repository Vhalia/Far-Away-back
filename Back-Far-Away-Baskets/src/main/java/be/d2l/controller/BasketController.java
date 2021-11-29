package be.d2l.controller;


import be.d2l.customExceptions.BasketAlreadyExistException;
import be.d2l.customExceptions.BasketNotFoundException;
import be.d2l.model.Basket;
import be.d2l.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/baskets")
public class BasketController {

    @Autowired
    private BasketService service;

    @GetMapping("/{idUser}")
    public ResponseEntity getBasketOfUser(@PathVariable("idUser") int idUser){
        if (idUser < 0) return ResponseEntity.badRequest().body("Malformed id user " + idUser);
        Iterable<Basket> baskets = service.findBasketOfUser(idUser);
        return ResponseEntity.ok(baskets);
    }
    @DeleteMapping
    public ResponseEntity<String> deleteProductOfBasket(@RequestBody Basket newBasket){
        if(newBasket.getIdProduct() < 0 ) return ResponseEntity.badRequest().body("Malformed id product " + newBasket.getIdProduct());
        if(newBasket.getIdUser() < 0) return ResponseEntity.badRequest().body("Malformed id user " + newBasket.getIdUser());
        try{
            service.deleteProductOfBasket(newBasket);
        }catch (BasketNotFoundException e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity updateProductQuantity(@RequestBody Basket newBasket, @RequestParam int quantity){
        if(newBasket.getIdProduct() < 0 ) return ResponseEntity.badRequest().body("Malformed id product " + newBasket.getIdProduct());
        if(newBasket.getIdUser() < 0) return ResponseEntity.badRequest().body("Malformed id user " + newBasket.getIdUser());
        if(quantity < 0 ) return ResponseEntity.badRequest().body("The new quantity is malformed ");
        try{
            return ResponseEntity.ok(service.updateProductQuantity(quantity,newBasket));
        }catch (BasketNotFoundException e){
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    public ResponseEntity addProductToBasket(@RequestBody Basket newBasket){
        if(newBasket.getIdProduct() < 0 ) return ResponseEntity.badRequest().body("Malformed id product " + newBasket.getIdProduct());
        if(newBasket.getIdUser() < 0) return ResponseEntity.badRequest().body("Malformed id user " + newBasket.getIdUser());
        try{
            return ResponseEntity.ok(service.addProductToBasket(newBasket));
        }catch (BasketAlreadyExistException e){
            return ResponseEntity.badRequest().body("This product is already in the basket");
        }

    }
}
