package pl.sztukakodu.taskette.tasks.boundary;

import lombok.Data;

@Data
class CreateTaskRequest {
    String title;
    String description;
    String attachmentComment;
}
