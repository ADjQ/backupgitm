package aiss.gitminer.controller;

import aiss.gitminer.exception.ProjectNotFoundException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.ProjectRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Tag(name = "Project" , description = "GitMiner")
@RestController
@RequestMapping("/gitminer/projects")

public class ProjectController {
    @Autowired
    ProjectRepository projectRepository;

    // GET http://localhost:8080/gitminer/projects
    @Operation(summary = "Get all projects", description = "Get all the projects",
            tags = { "get" , "id" })
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,
                    description = "Projects" ,
                    content = {@Content(schema = @Schema(implementation = Comment.class),
                            mediaType = "application/json")})
    })
    @GetMapping
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    // GET http://localhost:8080/gitminer/projects/15717393
    @Operation(summary = "Get project by ID", description = "Get a specific project using the project ID",
            tags = { "get", "id" })
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,
                    description = "Project" ,
                    content = {@Content(schema = @Schema(implementation = Comment.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "404" ,
                    description = "Project not found" ,
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable String id) throws ProjectNotFoundException {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isEmpty()) {
            throw new ProjectNotFoundException();
        }
        return project.get();
    }

    // GET http://localhost:8080/gitminer/projects?name=pitest
    @Operation(summary = "Get project by name", description = "Get a specific project using the project name",
            tags = { "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,
                    description = "Project" ,
                    content = {@Content(schema = @Schema(implementation = Comment.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "404" ,
                    description = "Project not found" ,
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping(params = "name")
    public Project getProjectByName(@RequestParam String name) throws ProjectNotFoundException {
        Optional<Project> project = projectRepository.findByName(name);
        if (project.isEmpty()) {
            throw new ProjectNotFoundException();
        }
        return project.get();
    }

    // POST http://localhost:8080/gitminer/projects
    @Operation(summary = "Create project", description = "Create a project",
            tags = { })
    @ApiResponses({
            @ApiResponse(responseCode = "201" ,
                    description = "Project" ,
                    content = {@Content(schema = @Schema(implementation = Project.class),
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "Project example",
                                    summary = "Example of a project with commits and issues",
                                    value = "{\n" +
                                            "    \"id\": \"15717393\",\n" +
                                            "    \"name\": \"pitest\",\n" +
                                            "    \"web_url\": \"https://github.com/hcoles/pitest\",\n" +
                                            "    \"commits\": [\n" +
                                            "        {\n" +
                                            "            \"id\": \"ee6e291274fcca03801261f1fd0684aa32c6d140\",\n" +
                                            "            \"title\": \"Merge pull request #1150 from hcoles/feature/results_interceptors\",\n" +
                                            "            \"message\": \"\\n\\nNew extension point\",\n" +
                                            "            \"author_name\": \"Henry Coles\",\n" +
                                            "            \"author_email\": \"henry.coles@googlemail.com\",\n" +
                                            "            \"authored_date\": \"2023-01-25T13:06:19Z\",\n" +
                                            "            \"web_url\": \"https://github.com/hcoles/pitest/commit/ee6e291274fcca03801261f1fd0684aa32c6d140\"\n" +
                                            "        },\n" +
                                            "        {\n" +
                                            "            \"id\": \"95e8102725b59780c07e89d4fca9a7563f12c976\",\n" +
                                            "            \"title\": \"New extension points\",\n" +
                                            "            \"message\": \"\\n\\nAdds new extension points to allow post analysis modification of\\ncoverage and mutation analysis results.\\n\\nExtensions points have multiple potential uses, but first use case is\\nsupporting the 'un-inlining' of inlined kotlin functions.\\n\\nChange requires alteration of existing interfaces which may be\\nincompatible with some third party plugins\",\n" +
                                            "            \"author_name\": \"Henry Coles\",\n" +
                                            "            \"author_email\": \"henry@pitest.org\",\n" +
                                            "            \"authored_date\": \"2023-01-12T09:29:33Z\",\n" +
                                            "            \"web_url\": \"https://github.com/hcoles/pitest/commit/95e8102725b59780c07e89d4fca9a7563f12c976\"\n" +
                                            "        }\n" +
                                            "    ],\n" +
                                            "    \"issues\": [\n" +
                                            "        {\n" +
                                            "            \"id\": \"1556497126\",\n" +
                                            "            \"title\": \"New extension points\",\n" +
                                            "            \"description\": \"Adds new extension points to allow post analysis modification of coverage and mutation analysis results.\\r\\n\\r\\nExtensions points have multiple potential uses, but first use case is supporting the 'un-inlining' of inlined kotlin functions.\\r\\n\\r\\nChange requires alteration of existing interfaces which may be incompatible with some third party plugins.\",\n" +
                                            "            \"state\": \"open\",\n" +
                                            "            \"created_at\": \"2023-01-25T11:35:30Z\",\n" +
                                            "            \"updated_at\": \"2023-01-25T13:06:20Z\",\n" +
                                            "            \"closed_at\": \"2023-01-25T13:06:19Z\",\n" +
                                            "            \"labels\": [],\n" +
                                            "            \"author\": {\n" +
                                            "                \"id\": \"1891135\",\n" +
                                            "                \"username\": \"hcoles\",\n" +
                                            "                \"name\": \"Henry Coles\",\n" +
                                            "                \"avatar_url\": \"https://avatars.githubusercontent.com/u/1891135?v=4\",\n" +
                                            "                \"web_url\": \"https://api.github.com/users/hcoles\"\n" +
                                            "            },\n" +
                                            "            \"assignee\": null,\n" +
                                            "            \"votes\": 0,\n" +
                                            "            \"comments\": []\n" +
                                            "        },\n" +
                                            "        {\n" +
                                            "            \"id\": \"1537933904\",\n" +
                                            "            \"title\": \"Fix spelling error in ComputClassWriter\",\n" +
                                            "            \"description\": null,\n" +
                                            "            \"state\": \"closed\",\n" +
                                            "            \"created_at\": \"2023-01-18T12:39:44Z\",\n" +
                                            "            \"updated_at\": \"2023-01-20T18:17:13Z\",\n" +
                                            "            \"closed_at\": \"2023-01-20T18:17:09Z\",\n" +
                                            "            \"labels\": [],\n" +
                                            "            \"author\": {\n" +
                                            "                \"id\": \"1671931\",\n" +
                                            "                \"username\": \"davidburstrom\",\n" +
                                            "                \"name\": null,\n" +
                                            "                \"avatar_url\": \"https://avatars.githubusercontent.com/u/1671931?v=4\",\n" +
                                            "                \"web_url\": \"https://api.github.com/users/davidburstrom\"\n" +
                                            "            },\n" +
                                            "            \"assignee\": null,\n" +
                                            "            \"votes\": 0,\n" +
                                            "            \"comments\": [\n" +
                                            "                {\n" +
                                            "                    \"id\": \"1398766669\",\n" +
                                            "                    \"body\": \"Thanks!\",\n" +
                                            "                    \"author\": {\n" +
                                            "                        \"id\": \"1891135\",\n" +
                                            "                        \"username\": \"hcoles\",\n" +
                                            "                        \"name\": \"Henry Coles\",\n" +
                                            "                        \"avatar_url\": \"https://avatars.githubusercontent.com/u/1891135?v=4\",\n" +
                                            "                        \"web_url\": \"https://api.github.com/users/hcoles\"\n" +
                                            "                    },\n" +
                                            "                    \"created_at\": \"2023-01-20T18:17:13Z\",\n" +
                                            "                    \"updated_at\": \"2023-01-20T18:17:13Z\"\n" +
                                            "                }\n" +
                                            "            ]\n" +
                                            "        }\n" +
                                            "    ]\n" +
                                            "}"
                            ))})
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Project createProject(@RequestBody @Valid Project project){
        Project newProject = new Project(project.getName(), project.getWebUrl());
        newProject.setId(project.getId());

        if (project.getCommits() != null) {
            newProject.setCommits(project.getCommits());
        }
        if (project.getIssues() != null) {
            newProject.setIssues(project.getIssues());
        }

        return projectRepository.save(newProject);
    }

    // PUT http://localhost:8080/gitminer/projects/{id}

    @Operation(summary = "Update project", description = "Update a specific project",
            tags = { })
    @ApiResponses({
            @ApiResponse(responseCode = "204" ,
                    description = "Project" ,
                    content = {@Content(schema = @Schema(implementation = Comment.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "404" ,
                    description = "Project not found" ,
                    content = {@Content(schema = @Schema())})
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProject(@RequestBody @Valid Project updatedProject, @PathVariable String id) throws ProjectNotFoundException {
        Optional<Project> projectData = projectRepository.findById(id);
        if (projectData.isEmpty()) {
            throw new ProjectNotFoundException();
        }

        Project _project = projectData.get();
        _project.setId(updatedProject.getId());
        _project.setName(updatedProject.getName());
        _project.setWebUrl(updatedProject.getWebUrl());
        _project.setCommits(updatedProject.getCommits());
        _project.setIssues(updatedProject.getIssues());
        projectRepository.save(_project);
    }

    // PUT http://localhost:8080/gitminer/projects/{id}
    @Operation(summary = "Delete project", description = "Delete a specific project",
            tags = { })
    @ApiResponses({
            @ApiResponse(responseCode = "204" ,
                    description = "Project" ,
                    content = {@Content(schema = @Schema(implementation = Comment.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "404" ,
                    description = "Project not found" ,
                    content = {@Content(schema = @Schema())})
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable String id) throws ProjectNotFoundException {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
        } else {
            throw new ProjectNotFoundException();
        }
    }
}
