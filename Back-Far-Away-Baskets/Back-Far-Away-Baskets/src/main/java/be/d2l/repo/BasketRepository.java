package be.d2l.repo;

import be.d2l.model.Basket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketRepository extends CrudRepository<Basket,Integer> {

    List<Basket> findByIdUser(int idUser);

}
