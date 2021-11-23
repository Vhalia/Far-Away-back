package be.d2l.service;

import be.d2l.customExceptions.CommentNotFoundException;
import be.d2l.model.Comment;
import be.d2l.repo.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository repo;

    public Iterable<Comment> findAllOrderByMostRecent() {return repo.findAllByOrderByCreationDateDesc();}

    public Iterable<Comment> findAllByIdProductAndNotIdUser(int idProduct, int idUser) {
        return repo.findAllByIdProductAndIdUserNotOrderByCreationDateDesc(idProduct, idUser);
    }

    public Iterable<Comment> findAllByIdProduct(int idProduct) {
        return repo.findALlByIdProductOrderByCreationDateDesc(idProduct);
    }

    public Iterable<Comment> findAllByIdProductAndIdUser(int idProduct, int idUser) {
        return repo.findAllByIdProductAndIdUserOrderByCreationDateDesc(idProduct, idUser);
    }

    public Comment save(Comment newComment) {return repo.save(newComment);}

    public void deleteComment(int idComment) throws CommentNotFoundException{
        if (!repo.existsById(idComment)) throw new CommentNotFoundException("no comment with id "+ idComment);
        repo.deleteById(idComment);
    }

    public Comment putComment(int idComment, Comment updateComment) throws CommentNotFoundException {
        Comment commentToUpdate = repo.findById(idComment).orElseThrow(() -> new CommentNotFoundException("No comment with id " + idComment));
        if (updateComment.getIsDeleted() != null)
            commentToUpdate.setIsDeleted(updateComment.getIsDeleted());
        if(updateComment.getText() != null)
            commentToUpdate.setText(updateComment.getText());
        return repo.save(commentToUpdate);
    }
}
