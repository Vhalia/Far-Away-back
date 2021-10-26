package be.d2l.service;

import be.d2l.model.Comment;
import be.d2l.repo.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository repo;

    public Iterable<Comment> findAll() {return repo.findAll();}
}
