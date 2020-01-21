package pl.sztukakodu.taskette.tags.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@Table(name = "tag")
@Entity
@NoArgsConstructor
public class Tag {
    @Id
    private Long id;
    private String name;

}
