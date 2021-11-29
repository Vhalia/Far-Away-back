package be.d2l.service;

import be.d2l.customExceptions.BasketAlreadyExistException;
import be.d2l.customExceptions.BasketNotFoundException;
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

    public void deleteProductOfBasket(Basket basketToDelete) throws BasketNotFoundException {
        basketToDelete = repo.findByIdProductAndIdUser(basketToDelete.getIdProduct(), basketToDelete.getIdUser());
        if(basketToDelete == null) throw new BasketNotFoundException("Product not in the basket");
        repo.deleteById(basketToDelete.getId());
    }

    public Basket updateProductQuantity(int quantity,Basket basketToUpdate) throws BasketNotFoundException{
        basketToUpdate = repo.findByIdProductAndIdUser(basketToUpdate.getIdProduct(), basketToUpdate.getIdUser());
        if(basketToUpdate == null) throw new BasketNotFoundException("Product not in the basket");
        basketToUpdate.setQuantity(quantity);
        return repo.save(basketToUpdate);
    }

    public Basket addProductToBasket(Basket basketToAdd)throws BasketAlreadyExistException {
        if(repo.existsBasketByIdProductAndIdUser(basketToAdd.getIdProduct(), basketToAdd.getIdUser())) throw new BasketAlreadyExistException("This product is already in the basket");
        basketToAdd.setQuantity(1);
        return repo.save(basketToAdd);
    }
}
