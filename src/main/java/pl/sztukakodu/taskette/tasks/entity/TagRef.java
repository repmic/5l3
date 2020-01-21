package pl.sztukakodu.taskette.tasks.entity;

import lombok.Data;
import pl.sztukakodu.taskette.tags.entity.Tag;

import javax.persistence.Table;

@Data
@Table(name = "tag_task")
public class TagRef {
    private Long tag;

    public TagRef() {
    }

    public TagRef(Tag tag) {
        this.tag = tag.getId();
    }
}
