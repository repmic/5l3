package pl.sztukakodu.taskette.tasks;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.tasks")
@Data
public class TasksConfig {

    private String endpointMessage;

}
