package be.d2l.repo;

import be.d2l.model.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer>{
    public Iterable<Comment> findAllByIdProductAndNotIdUser(int idProduct, int idUser);
}
