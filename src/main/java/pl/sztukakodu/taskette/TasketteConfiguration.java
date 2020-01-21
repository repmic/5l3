package pl.sztukakodu.taskette;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.sztukakodu.taskette.tasks.boundary.FileSystemStorageService;
import pl.sztukakodu.taskette.tasks.boundary.StorageService;

import java.nio.file.Path;

@Slf4j
@Configuration
public class TasketteConfiguration {

    @Bean
    public Clock clock() {
        log.info("Registering Clock as Spring bean");
        return new SystemClock();
    }

    @Bean
    StorageService storageService() {
        return new FileSystemStorageService(
            Path.of("/opt/taskette")
        );
    }

}
