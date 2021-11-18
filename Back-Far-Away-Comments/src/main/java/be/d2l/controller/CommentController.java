package be.d2l.controller;

import be.d2l.model.Comment;
import be.d2l.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;
import java.util.Date;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService service;

    @GetMapping
    public Iterable<Comment> getComments() {
        return service.findAll();
    }

    @GetMapping("{idProduct}")
    public Iterable<Comment> getCommentsByIdProductExceptUser(@PathVariable("idProduct") int idProduct,
                                                              @RequestParam(required = false) int idUser) {
        return service.findAllByIdProductAndNotIdUser(idProduct, idUser);
    }

    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestBody Comment newComment) {
        if (newComment == null)
            return ResponseEntity.noContent().build();
        else if(!newComment.isValid())
            return ResponseEntity.badRequest().build();
        newComment.setCreationDate(new Date());
        newComment.setDeleted(false);
        return ResponseEntity.ok(service.save(newComment));
    }
}
