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

    public void deleteProductOfBasket(int idProduct, int idUser) throws BasketNotFoundException {
        if(!repo.existsBasketByIdProductAndIdUser(idProduct, idUser)) throw new BasketNotFoundException("Product not in the basket");
        Basket basketToDelete = repo.findByIdProductAndIdUser(idProduct,idUser);
        repo.deleteById(basketToDelete.getId());
    }

    public Basket updateProductQuantity(int quantity,int idProduct, int idUser) throws BasketNotFoundException{
        if(!repo.existsBasketByIdProductAndIdUser(idProduct, idUser)) throw new BasketNotFoundException("Product not in the basket");
        Basket basketToUpdate = repo.findByIdProductAndIdUser(idProduct,idUser);
        basketToUpdate.setQuantity(quantity);
        return repo.save(basketToUpdate);
    }

    public Basket addProductToBasket(int idProduct, int idUser)throws BasketAlreadyExistException {
        if(repo.existsBasketByIdProductAndIdUser(idProduct, idUser)) throw new BasketAlreadyExistException("This product is already in the basket");
        return repo.save(new Basket(idUser,idProduct,1));
    }
}
