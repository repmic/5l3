package pl.sztukakodu.taskette.tags.control;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sztukakodu.taskette.exceptions.NotFoundException;
import pl.sztukakodu.taskette.tags.boundary.TagsCrudRepository;
import pl.sztukakodu.taskette.tags.entity.Tag;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class TagsService {

    private final TagsCrudRepository tagsRepository;

    public Tag findById(Long id) {
        return tagsRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Tag not found for id: " + id));
    }

    public Set<Tag> findAllById(List<Long> tagIds) {
        return StreamSupport.stream(
            tagsRepository.findAllById(tagIds).spliterator(), false
        ).collect(Collectors.toSet());
    }
}
