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
    @DeleteMapping("/{idProduct}/{idUser}")
    public ResponseEntity<String> deleteProductOfBasket(@PathVariable("idProduct") int idProduct, @PathVariable("idUser") int idUser){
        if(idProduct < 0 ) return ResponseEntity.badRequest().body("Malformed id product " + idProduct);
        if(idUser < 0) return ResponseEntity.badRequest().body("Malformed id user " + idUser);
        try{
            service.deleteProductOfBasket(idProduct,idUser);
        }catch (BasketNotFoundException e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idProduct}/{idUser}")
    public ResponseEntity updateProductQuantity(@RequestBody int quantity, @PathVariable("idProduct") int idProduct, @PathVariable("idUser") int idUser){
        System.out.println("j'ai trouvé la route");
        if(idProduct < 0 ) return ResponseEntity.badRequest().body("Malformed id product " + idProduct);
        if(idUser < 0) return ResponseEntity.badRequest().body("Malformed id user " + idUser);
        if(quantity < 0 ) return ResponseEntity.badRequest().body("The new quantity is malformed ");
        try{
            return ResponseEntity.ok(service.updateProductQuantity(quantity,idProduct,idUser));
        }catch (BasketNotFoundException e){
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/{idProduct}/{idUser}")
    public ResponseEntity addProductToBasket(@PathVariable("idProduct") int idProduct, @PathVariable("idUser") int idUser){
        if(idProduct < 0 ) return ResponseEntity.badRequest().body("Malformed id product " + idProduct);
        if(idUser < 0) return ResponseEntity.badRequest().body("Malformed id user " + idUser);
        try{
            return ResponseEntity.ok(service.addProductToBasket(idProduct,idUser));
        }catch (BasketAlreadyExistException e){
            return ResponseEntity.badRequest().build();
        }

    }
}
