package aiss.gitminer.controller;

import aiss.gitminer.exception.CommentNotFoundException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.repository.CommentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Comment" , description = "Comments")
@RequestMapping("/gitminer/comments")

public class CommentController {
    @Autowired
    CommentRepository commentRepository;

    // GET http://localhost:8080/gitminer/comments
    @Operation(summary = "Get all comments", description = "Get all the comments",
            tags = { "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,
                    description = "List of comments" ,
                    content = {@Content(schema = @Schema(implementation = Comment.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "404" ,
                    description = "Comments not found" ,
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping
    public @ResponseBody Iterable<Comment> getComments() {
        return commentRepository.findAll();
    }

    // GET http://localhost:8080/gitminer/comments/1398766669
    @Operation(summary = "Get comment by ID", description = "Get a specific comment using the comment ID",
            tags = { "get", "id" })
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,
                    description = "Comment" ,
                    content = {@Content(schema = @Schema(implementation = Comment.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "404" ,
                    description = "Comment not found" ,
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{id}")
    public @ResponseBody Comment getCommentById(@Parameter(name = "id", description = "ID of comment", example = "1398766669") @PathVariable String id) throws CommentNotFoundException {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty()) {
            throw new CommentNotFoundException();
        }
        return comment.get();
    }

    // GET http://localhost:8080/gitminer/comments?authorId=1891135
    @Operation(summary = "Get comments by author ID", description = "Get a list of comments using the author ID",
            tags = { "get", "id" })
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,
                    description = "Comments" ,
                    content = {@Content(schema = @Schema(implementation = Comment.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "404" ,
                    description = "Comment not found" ,
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping(params = "authorId")
    public List<Comment> getCommentByAuthorId(@Parameter(name = "id", description = "ID of author", example = "1891135") @RequestParam String authorId) throws CommentNotFoundException {
        Optional<List<Comment>> comment = commentRepository.findByAuthor_Id(authorId);
        if (comment.isEmpty()) {
            throw new CommentNotFoundException();
        }
        return comment.get();
    }
}
