# GitMiner
Herramienta de minería para proyectos basados en Git. Este proyecto cuenta con dos APIs más (GitHubMiner y BitBucketMiner) para transformar los modelos de ambas APIs al modelo de datos definido en esta API.

Para acceder a la base de datos H2 (ver datos de acceso en _application.properties_):
http://localhost:8080/h2-ui/

Se cuenta de una colección de tests de Postman incluidos en la entrega del proyecto (GitMiner Tests)

También se cuenta de una documentación hecha en Swagger para este proyecto:
http://localhost:8080/swagger-ui/index.html#/

### Estructura del proyecto:
```
aiss.gitminer
    controller
        CommentController
        CommitController
        IssueController
        ProjectController
        
    exception
        GlobalExceptionHandler
        CommentNotFoundException
        CommitNotFoundException
        IssueNotFoundException
        ProjectNotFoundException
        
    model
        Comment
        Commit
        Issue
        Project
        User
        
    repository
        CommentRepository
        CommitRepository
        IssueRepository
        ProjectRepository

    GitHubMinerApplication 
    
resources
    application.properties
```

### Métodos implementados:
- **CommentController**:
  - getComments
  - getCommentById
  - getCommentsByAuthorId <sup>+</sup>
- **CommitController**:
  - getCommits
  - getCommitById
- **IssueController**:
  - getIssues
  - getIssueById
  - getCommentsOfIssue
  - getIssuesByAuthorId
  - getIssuesByState
- **ProjectController**:
  - getProjects
  - getProjectById
  - getProjectByName <sup>+</sup>
  - createProject
  - updateProject
  - deleteProject

_<sup>+</sup> = métodos añadidos que no eran obligatorios de implementar_