package aiss.gitminer.controller;

import aiss.gitminer.model.Issue;
import aiss.gitminer.model.Comment;
import aiss.gitminer.exception.IssueNotFoundException;
import aiss.gitminer.repository.IssueRepository;
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

@Tag(name = "Issue" , description = "Issues")
@RestController
@RequestMapping("/gitminer/issues")

public class IssueController {

    @Autowired
    IssueRepository issueRepository;

    // GET http://localhost:8080/gitminer/issues
    @Operation(summary = "Get all issues", description = "Get all the issues",
            tags = { "issues", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,
                    description = "Issues" ,
                    content = {@Content(schema = @Schema(implementation = Comment.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "404" ,
                    description = "Issue not found" ,
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping
    public List<Issue> getIssues() {
        return issueRepository.findAll();
    }

    // GET http://localhost:8080/gitminer/issues/1556497126
    @Operation(summary = "Get issue by ID", description = "Get a specific issue using the issue ID",
            tags = { "get", "id" })
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,
                    description = "Issue" ,
                    content = {@Content(schema = @Schema(implementation = Comment.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "404" ,
                    description = "Issue not found" ,
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{id}")
    public Issue getIssueById(@Parameter(name = "id", description = "ID of issue", example = "1556497126") @PathVariable String id) throws IssueNotFoundException {
        Optional<Issue> issue = issueRepository.findById(id);
        if (issue.isEmpty()) {
            throw new IssueNotFoundException();
        }
        return issue.get();
    }

    // GET http://localhost:8080/gitminer/issues/1554713335/comments
    @Operation(summary = "Get issue's comments", description = "Get the comments of a specific issue",
            tags = { "get", "id" })
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,
                    description = "Comments" ,
                    content = {@Content(schema = @Schema(implementation = Comment.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "404" ,
                    description = "Issue not found" ,
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{id}/comments")
    public List<Comment> getCommentsOfIssue(@Parameter(name = "id", description = "ID of issue", example = "1554713335")@PathVariable String id) throws IssueNotFoundException {
        Optional<Issue> issue = issueRepository.findById(id);
        if (issue.isEmpty()) {
            throw new IssueNotFoundException();
        }
        return issue.get().getComments();
    }

    // GET http://localhost:8080/gitminer/issues?authorId=5122337
    @Operation(summary = "Get issues by author ID", description = "Get the issues of a specific author using the author ID " +
            " (Por alguna razon, en la documentacion aparece el GET del author ID y el GET del estado como si los dos fuesen un solo GET, pero en el IssueController ambos GET estan implementados correctamente y separados)",
            tags = { "get", "id" })
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,
                    description = "Issue" ,
                    content = {@Content(schema = @Schema(implementation = Comment.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "404" ,
                    description = "Issue not found" ,
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping(params = "authorId")
    public List<Issue> getIssuesByAuthorId(@Parameter(name = "authorId", description = "ID of the author", example = "5122337")@RequestParam String authorId) throws IssueNotFoundException {
        Optional<List<Issue>> issue = issueRepository.findByAuthor_Id(authorId);
        if (issue.isEmpty()) {
            throw new IssueNotFoundException();
        }
        return issue.get();
    }

    // GET http://localhost:8080/gitminer/issues?state=open
    @Operation(summary = "Get issues by state", description = "Get the issues that are on a specific state",
            tags = { "get", "filter" })
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,
                    description = "Issue" ,
                    content = {@Content(schema = @Schema(implementation = Comment.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "404" ,
                    description = "Issue not found" ,
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping(params = "state")
    public List<Issue> getIssuesByState(@Parameter(name = "state", description = "State of the issue", example = "open") @RequestParam String state) throws IssueNotFoundException {
        Optional<List<Issue>> issue = issueRepository.findByState(state);
        if (issue.isEmpty()) {
            throw new IssueNotFoundException();
        }
        return issue.get();
    }

}
