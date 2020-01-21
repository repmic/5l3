package pl.sztukakodu.taskette.tasks.boundary;

import org.springframework.stereotype.Repository;
import pl.sztukakodu.taskette.exceptions.NotFoundException;
import pl.sztukakodu.taskette.tasks.entity.Task;

import java.util.*;

@Repository
class MemoryTasksRepository implements TasksRepository {
    private final Set<Task> tasks = new HashSet<>();

    @Override
    public void add(Task task) {
        tasks.add(task);
    }

    @Override
    public List<Task> fetchAll() {
        return new ArrayList<>(tasks);
    }

    @Override
    public Task fetchById(Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
    }

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(tasks::remove);
    }

    @Override
    public void update(Long id, String title, String description) {
        Task task = findById(id)
            .orElseThrow(() -> new NotFoundException("Task with id not found: " + id));
        task.setTitle(title);
        task.setDescription(description);
    }

    @Override
    public void save(Task task) {
        tasks.add(task);
    }

    @Override
    public List<Task> findByTitle(String title) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Task> findWithAttachments() {
        throw new UnsupportedOperationException();
    }

    private Optional<Task> findById(Long id) {
        return tasks.stream()
            .filter(task -> id.equals(task.getId()))
            .findFirst();
    }
}
