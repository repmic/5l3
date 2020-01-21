package pl.sztukakodu.taskette.tasks.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "attachment")
@AllArgsConstructor
public class Attachment {
    private String filename;
    private String comment;
}
