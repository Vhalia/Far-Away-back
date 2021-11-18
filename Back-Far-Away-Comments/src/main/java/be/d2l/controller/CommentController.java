package be.d2l.controller;

import be.d2l.customExceptions.CommentNotFoundException;
import be.d2l.model.Comment;
import be.d2l.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.ws.rs.PathParam;
import java.net.URI;
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
        Comment createdComment = service.save(newComment);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdComment.getId()).toUri();
        return ResponseEntity.created(location).body(createdComment);
    }

    @DeleteMapping("{idComment}")
    public ResponseEntity<Void> deleteComment(@PathVariable("idComment") int idComment) {
        if (idComment < 0) return ResponseEntity.badRequest().build();
        try {
            service.deleteComment(idComment);
        } catch (CommentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
