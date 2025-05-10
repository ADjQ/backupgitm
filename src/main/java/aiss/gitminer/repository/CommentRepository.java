package aiss.gitminer.repository;

import aiss.gitminer.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
    Optional<List<Comment>> findByAuthor_Id(String authorId);
}
