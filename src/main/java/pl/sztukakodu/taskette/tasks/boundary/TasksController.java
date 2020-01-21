package pl.sztukakodu.taskette.tasks.boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.sztukakodu.taskette.exceptions.NotFoundException;
import pl.sztukakodu.taskette.tags.control.TagsService;
import pl.sztukakodu.taskette.tags.entity.Tag;
import pl.sztukakodu.taskette.tasks.control.TasksService;
import pl.sztukakodu.taskette.tasks.entity.TagRef;
import pl.sztukakodu.taskette.tasks.entity.Task;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
@RequestMapping(path = "/api/tasks")
@RequiredArgsConstructor
@CrossOrigin
class TasksController {
    private final TasksRepository tasksRepository;
    private final TasksService tasksService;
    private final TagsService tagsService;

    @GetMapping
    public List<TaskResponse> getTasks(@RequestParam Optional<String> title) {
        log.info("Fetching all tasks with query: {}", title);
        return toTaskResponse(
            title.map(tasksService::filterByTitle)
                .orElseGet(tasksService::fetchAll)
        );
    }

    @GetMapping(path = "/_search")
    public List<TaskResponse> searchTasks(@RequestParam(defaultValue = "false") Boolean attachments) {
        if (attachments) {
            return toTaskResponse(tasksService.findWithAttachments());
        } else {
            return toTaskResponse(tasksService.fetchAll());
        }
    }

    private List<TaskResponse> toTaskResponse(List<Task> tasks) {
        return tasks.stream().map(this::toTaskResponse).collect(toList());
    }

    private TaskResponse toTaskResponse(Task task) {
        List<Long> tagIds = task.getTagRefs().stream().map(TagRef::getTag).collect(toList());
        Set<Tag> tags = tagsService.findAllById(tagIds);
        return TaskResponse.from(task, tags);
    }

    @GetMapping(path = "/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
        log.info("Fetching task with id: {}", id);
        return toTaskResponse(tasksRepository.fetchById(id));
    }

    @PostMapping(path = "/{id}/tags")
    public ResponseEntity addTag(@PathVariable Long id, @RequestBody AddTagRequest request) {
        tasksService.addTag(id, request.tagId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{id}/tags/{tagId}")
    public ResponseEntity removeTag(@PathVariable Long id, @PathVariable Long tagId) {
        tasksService.removeTag(id, tagId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping(path = "/{id}/attachments/{filename}")
    public ResponseEntity getAttachment(
        @PathVariable("id") Long taskId,
        @PathVariable String filename,
        HttpServletRequest request) throws IOException {
        Optional<Resource> attachment = tasksService.loadAttachment(taskId, filename);
        if (attachment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Resource resource = attachment.get();
        String mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(mimeType))
            .body(resource);
    }


    @PostMapping(path = "/{id}/attachments")
    public ResponseEntity addAttachment(
        @PathVariable("id") Long taskId,
        @RequestParam("comment") String comment,
        @RequestParam("file") MultipartFile file) throws IOException {
        tasksService.addTaskAttachment(taskId, file, comment);
        return ResponseEntity.noContent().build();
    }


    @PostMapping
    public void addTask(@RequestBody CreateTaskRequest task) {
        log.info("Storing new task: {}", task);
        tasksService.addTask(task.title, task.description);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteTask(@PathVariable Long id) {
        log.info("Deleting task with id: {}", id);
        tasksRepository.deleteById(id);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity updateTask(
        @PathVariable Long id,
        @RequestBody UpdateTaskRequest request) {
        try {
            tasksService.updateTask(id, request.title, request.description);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            log.error("Unable to update task", e);
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
        }
    }

}
