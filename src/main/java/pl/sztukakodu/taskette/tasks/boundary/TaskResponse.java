package pl.sztukakodu.taskette.tasks.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.sztukakodu.taskette.tags.entity.Tag;
import pl.sztukakodu.taskette.tasks.entity.Attachment;
import pl.sztukakodu.taskette.tasks.entity.Task;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Data
@AllArgsConstructor
class TaskResponse {
    long id;
    String title;
    String description;
    LocalDateTime createdAt;
    Set<AttachmentResponse> attachments;
    Set<TagResponse> tag;

    static TaskResponse from(Task task, Set<Tag> tags) {
        return new TaskResponse(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getCreatedAt(),
            task.getAttachments()
                .stream()
                .map(AttachmentResponse::from)
                .collect(toSet()),
            tags.stream().map(TagResponse::new).collect(toSet())
        );
    }
}
