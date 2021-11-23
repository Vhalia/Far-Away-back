package be.d2l.controller;

import be.d2l.customExceptions.CommentNotFoundException;
import be.d2l.model.Comment;
import be.d2l.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService service;

    @GetMapping
    public Iterable<Comment> getComments() {
        return service.findAllOrderByMostRecent();
    }

    @GetMapping("{idProduct}")
    public ResponseEntity getCommentsByIdProductExceptUser(@PathVariable("idProduct") int idProduct,
                                                              @RequestParam(required = false) Integer idUser) {
        if (idProduct < 0) return ResponseEntity.badRequest().body("Malformed product id " + idProduct);
        if (idUser == null)
            return ResponseEntity.ok(service.findAllByIdProduct(idProduct));
        if(idUser < 0 ) return ResponseEntity.badRequest().body("Malformed user id " + idUser);
        return ResponseEntity.ok(service.findAllByIdProductAndNotIdUser(idProduct, idUser));
    }

    @GetMapping("{idProduct}/{idUser}")
    public ResponseEntity getCommentsByIdProductAndIdUser(@PathVariable("idProduct") int idProduct,
                                                                             @PathVariable("idUser") int idUser) {
        if(idProduct < 0 || idUser < 0) return ResponseEntity.badRequest().body("Malformed user or product id");
        return ResponseEntity.ok(service.findAllByIdProductAndIdUser(idProduct, idUser));
    }

    @PostMapping
    public ResponseEntity addComment(@RequestBody Comment newComment) {
        if (newComment == null)
            return ResponseEntity.badRequest().body("Empty body");
        else if(!newComment.checkValidity())
            return ResponseEntity.badRequest().body("Missing mandatory information");
        newComment.setCreationDate(new Date());
        newComment.setIsDeleted(false);
        Comment createdComment = service.save(newComment);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdComment.getId()).toUri();
        return ResponseEntity.created(location).body(createdComment);
    }

    @DeleteMapping("{idComment}")
    public ResponseEntity<String> deleteComment(@PathVariable("idComment") int idComment) {
        if (idComment < 0) return ResponseEntity.badRequest().body("Malformed comment id " + idComment);
        try {
            service.deleteComment(idComment);
        } catch (CommentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("{idComment}")
    public ResponseEntity updateComment(@PathVariable("idComment") int idComment, @RequestBody Comment updateComment) {
        if (updateComment == null) return ResponseEntity.badRequest().body("Empty body");
        else if (idComment < 0) return ResponseEntity.badRequest().body("Malformed comment id " +idComment);
        try {
            return ResponseEntity.ok(service.putComment(idComment, updateComment));
        }catch(CommentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
