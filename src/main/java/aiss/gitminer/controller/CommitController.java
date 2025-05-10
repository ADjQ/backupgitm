package aiss.gitminer.controller;

import aiss.gitminer.exception.CommitNotFoundException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Commit;
import aiss.gitminer.repository.CommitRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Commit" , description = "Commits")
@RestController
@RequestMapping("/gitminer/commits")

public class CommitController {
    @Autowired
    CommitRepository commitRepository;

    // GET http://localhost:8080/gitminer/commits
    @Operation(summary = "Get all commits", description = "Get a list of all the commits",
            tags = { "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,
                    description = "Commits" ,
                    content = {@Content(schema = @Schema(implementation = Comment.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "404" ,
                    description = "Comments not found" ,
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping
    public List<Commit> getCommits() {
        return commitRepository.findAll();
    }

    // GET http://localhost:8080/gitminer/commits/ee6e291274fcca03801261f1fd0684aa32c6d140
    @Operation(summary = "Get commit by ID", description = "Get a specific commit using the commit ID",
            tags = { "get", "id" })
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,
                    description = "Commit" ,
                    content = {@Content(schema = @Schema(implementation = Comment.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "404" ,
                    description = "Commit not found" ,
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{id}")
    public Commit getCommitById(@Parameter(name = "id", description = "ID of commit", example = "ee6e291274fcca03801261f1fd0684aa32c6d140") @PathVariable String id) throws CommitNotFoundException {
        Optional<Commit> commit = commitRepository.findById(id);
        if (commit.isEmpty()) {
            throw new CommitNotFoundException();
        }
        return commit.get();
    }
}
