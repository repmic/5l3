package pl.sztukakodu.taskette.tasks.boundary;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.sztukakodu.taskette.tasks.entity.Task;

import java.util.List;

interface TasksCrudRepository extends JpaRepository<Task, Long> {

    @Modifying
    @Query("UPDATE Task SET title = :title, description = :description WHERE id = :id")
    void updateTitleDescription(@Param("id") Long id, @Param("title") String title, @Param("description") String description);

    @Query
    List<Task> findAllByTitleLike(String title);


  //  @Query("SELECT * FROM Task T JOIN Attachment a ON t.id = a.task")
  //  List<Task> findWithAttachments();
}
