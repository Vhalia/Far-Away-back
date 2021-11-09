package be.d2l.service;

import be.d2l.model.Basket;
import be.d2l.repo.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasketService {

    @Autowired
    private BasketRepository repo;

    public Iterable<Basket> findBasketOfUser(int idUser){
        return repo.findByIdUser(idUser);
    }

}
