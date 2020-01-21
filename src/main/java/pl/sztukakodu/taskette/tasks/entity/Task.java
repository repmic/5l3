package pl.sztukakodu.taskette.tasks.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sztukakodu.taskette.tags.entity.Tag;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Table(name = "task")
@Entity
@NoArgsConstructor
public class Task {
    @Id
    @SequenceGenerator(name = "task_id_generator")
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private transient Set<Attachment> attachments;
    private transient Set<TagRef> tagRefs;

    public Task(String title, String description, LocalDateTime createdAt) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }

    public void addAttachment(String filename, String comment) {
        attachments.add(new Attachment(filename, comment));
    }

    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public void addTag(Tag tag) {
        tagRefs.add(new TagRef(tag));
    }

    public void removeTag(Tag tag) {
        tagRefs.remove(new TagRef(tag));
    }
}
