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
    public ResponseEntity<String> deleteProductOfBasket(@RequestBody Basket BasketToDelete){
        if(BasketToDelete.getIdProduct() < 0 ) return ResponseEntity.badRequest().body("Malformed id product " + BasketToDelete.getIdProduct());
        if(BasketToDelete.getIdUser() < 0) return ResponseEntity.badRequest().body("Malformed id user " + BasketToDelete.getIdUser());
        try{
            service.deleteProductOfBasket(BasketToDelete);
        }catch (BasketNotFoundException e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity updateProductQuantity(@RequestBody Basket BasketToUpdate, @RequestParam int quantity){
        if(BasketToUpdate.getIdProduct() < 0 ) return ResponseEntity.badRequest().body("Malformed id product " + BasketToUpdate.getIdProduct());
        if(BasketToUpdate.getIdUser() < 0) return ResponseEntity.badRequest().body("Malformed id user " + BasketToUpdate.getIdUser());
        if(quantity < 0 ) return ResponseEntity.badRequest().body("The new quantity is malformed ");
        try{
            return ResponseEntity.ok(service.updateProductQuantity(quantity,BasketToUpdate));
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
