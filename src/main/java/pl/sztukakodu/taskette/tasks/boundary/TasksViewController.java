package pl.sztukakodu.taskette.tasks.boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.sztukakodu.taskette.tasks.control.TasksService;
import pl.sztukakodu.taskette.tasks.entity.Task;

import java.io.IOException;

import static java.util.stream.Collectors.toList;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TasksViewController {

    private final TasksService tasksService;

    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("tasks", tasksService.fetchAll().stream().map(TaskViewResponse::from).collect(toList()));
        model.addAttribute("newTask", new CreateTaskRequest());
        return "home";
    }

    @PostMapping("/tasks")
    public String addTask(@ModelAttribute("newTask") CreateTaskRequest request,
                          @RequestParam("attachment") MultipartFile attachment) throws IOException {
        Task task = tasksService.addTask(request.title, request.description);
        tasksService.addTaskAttachment(task.getId(), attachment, request.attachmentComment);
        return "redirect:/";
    }

    @PostMapping("/tasks/delete/{id}")
    public String removeTask(@PathVariable Long id) {
        tasksService.deleteTask(id);
        return "redirect:/";
    }

}
