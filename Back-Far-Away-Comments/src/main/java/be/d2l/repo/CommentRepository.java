package be.d2l.repo;

import be.d2l.model.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer>{
    public Iterable<Comment> findAllByOrderByCreationDateDesc();
    public Iterable<Comment> findAllByIdProductAndIdUserNotOrderByCreationDateDesc(int idProduct, int idUser);
    public Iterable<Comment> findAllByIdProductAndIdUserOrderByCreationDateDesc(int idProduct, int idUser);
    public Iterable<Comment> findALlByIdProductOrderByCreationDateDesc(int idProduct);
    public int countByIdProduct(int idProduct);
}
