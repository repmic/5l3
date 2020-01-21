package pl.sztukakodu.taskette.tasks.boundary;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import pl.sztukakodu.taskette.exceptions.NotFoundException;
import pl.sztukakodu.taskette.tasks.entity.Task;

import java.util.Collections;
import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Primary
@Repository
@RequiredArgsConstructor
public class AdaptedTasksCrudRepository implements TasksRepository {
    private final TasksCrudRepository tasksRepository;

    @Override
    public void add(Task task) {
        tasksRepository.save(task);
    }

    @Override
    public List<Task> fetchAll() {
        return StreamSupport
            .stream(tasksRepository.findAll().spliterator(), false)
            .collect(toList());
    }

    @Override
    public Task fetchById(Long id) {
        return tasksRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Cannot find task with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        tasksRepository.deleteById(id);
    }

    @Override
    public void update(Long id, String title, String description) {
        tasksRepository.updateTitleDescription(id, title, description);
    }

    @Override
    public void save(Task task) {
        tasksRepository.save(task);
    }

    @Override
    public List<Task> findByTitle(String title)
    {
        return tasksRepository.findAllByTitleLike(title);
    }

    @Override
    public List<Task> findWithAttachments() {
        return Collections.emptyList();
        //return tasksRepository.findWithAttachments();
    }
}
