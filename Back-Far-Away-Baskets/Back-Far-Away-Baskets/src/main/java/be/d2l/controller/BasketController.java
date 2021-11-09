package be.d2l.controller;


import be.d2l.model.Basket;
import be.d2l.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/baskets")
public class BasketController {

    @Autowired
    private BasketService service;

    @GetMapping("/{idUser}")
    public Iterable<Basket> getBasketOfUser(@PathVariable("idUser") int idUser){
        return service.findBasketOfUser(idUser);
    }
}
