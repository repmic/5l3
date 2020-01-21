package pl.sztukakodu.taskette.tasks.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.sztukakodu.taskette.tags.entity.Tag;
import pl.sztukakodu.taskette.tasks.entity.Task;

import java.time.LocalDateTime;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Data
@AllArgsConstructor
class TaskViewResponse {
    long id;
    String title;
    String description;
    LocalDateTime createdAt;

    static TaskViewResponse from(Task task) {
        return new TaskViewResponse(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getCreatedAt()
        );
    }
}
