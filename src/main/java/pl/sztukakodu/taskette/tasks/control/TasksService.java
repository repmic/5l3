package pl.sztukakodu.taskette.tasks.control;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.sztukakodu.taskette.Clock;
import pl.sztukakodu.taskette.tags.control.TagsService;
import pl.sztukakodu.taskette.tags.entity.Tag;
import pl.sztukakodu.taskette.tasks.boundary.StorageService;
import pl.sztukakodu.taskette.tasks.boundary.TasksRepository;
import pl.sztukakodu.taskette.tasks.entity.Attachment;
import pl.sztukakodu.taskette.tasks.entity.Task;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class TasksService {
    private final StorageService storageService;
    private final TasksRepository tasksRepository;
    private final TagsService tagsService;
    private final Clock clock;

    public Task addTask(String title, String description) {
        Task task = new Task(
            title,
            description,
            clock.time()
        );
        tasksRepository.add(task);
        return task;
    }

    public void addTaskAttachment(Long taskId, MultipartFile attachment, String comment) throws IOException {
        Task task = tasksRepository.fetchById(taskId);
        if (!attachment.isEmpty()) {
            String filename = storageService.saveFile(attachment);
            task.addAttachment(filename, comment);
        }
        tasksRepository.save(task);
    }

    public void updateTask(Long id, String title, String description) {
        tasksRepository.update(id, title, description);
    }

    public List<Task> fetchAll() {
        return tasksRepository.fetchAll();
    }

    public List<Task> filterByTitle(String title) {
        return tasksRepository.findByTitle(title);
    }

    public void deleteTask(Long id) {
        tasksRepository.deleteById(id);
    }

    public Optional<Resource> loadAttachment(Long id, String filename) throws MalformedURLException {
        Optional<Resource> attachment = Optional.empty();
        Task task = tasksRepository.fetchById(id);
        if (task.getAttachments().stream().map(Attachment::getFilename).anyMatch(it -> it.equals(filename))) {
            attachment = Optional.of(storageService.loadFile(filename));
        }
        return attachment;
    }

    public void addTag(Long id, Long tagId) {
        Task task = tasksRepository.fetchById(id);
        Tag tag = tagsService.findById(tagId);
        task.addTag(tag);
        tasksRepository.save(task);
    }

    public void removeTag(Long id, Long tagId) {
        Task task = tasksRepository.fetchById(id);
        Tag tag = tagsService.findById(tagId);
        task.removeTag(tag);
        tasksRepository.save(task);
    }

    public List<Task> findWithAttachments() {
        return tasksRepository.findWithAttachments();
    }
}
