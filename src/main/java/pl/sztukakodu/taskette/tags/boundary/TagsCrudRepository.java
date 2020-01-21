package pl.sztukakodu.taskette.tags.boundary;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.sztukakodu.taskette.tags.entity.Tag;

public interface TagsCrudRepository extends JpaRepository<Tag, Long> {
}
