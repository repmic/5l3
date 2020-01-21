package pl.sztukakodu.taskette.tasks.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.sztukakodu.taskette.tasks.entity.Attachment;

@Data
@AllArgsConstructor
class AttachmentResponse {
    String filename;
    String comment;

    static AttachmentResponse from(Attachment it) {
        return new AttachmentResponse(
          it.getFilename(),
          it.getComment()
        );
    }
}
