package pl.sztukakodu.taskette.tasks.boundary;

import lombok.Data;

@Data
class UpdateTaskRequest {
    String title;
    String description;
}
