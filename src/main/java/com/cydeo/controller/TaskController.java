package com.cydeo.controller;

import com.cydeo.dto.TaskDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/task")
public class TaskController {

    private final UserService userService;
    private final TaskService taskService;
    private final ProjectService projectService;

    public TaskController(UserService userService, TaskService taskService, ProjectService projectService) {
        this.userService = userService;
        this.taskService = taskService;
        this.projectService = projectService;
    }

    @GetMapping("/create")
    public String createTask(Model model){

        model.addAttribute("task",new TaskDTO());
        model.addAttribute("projects",projectService.findAll());
        model.addAttribute("employees",userService.findEmployees());
        model.addAttribute("tasks",taskService.findAll());

        return "/task/create";
    }

    @PostMapping("/create")
    public String insertTask(@Valid @ModelAttribute("task") TaskDTO task, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            model.addAttribute("projects",projectService.findAll());
            model.addAttribute("employees",userService.findEmployees());
            model.addAttribute("tasks",taskService.findAll());
            return "/task/create";
        }

        taskService.save(task);

        return "redirect:/task/create";
    }

    @GetMapping("/update/{id}")
    public String editTask(@PathVariable("id") Long id, Model model){

        model.addAttribute("task",taskService.findById(id));
        model.addAttribute("projects",projectService.findAll());
        model.addAttribute("employees",userService.findEmployees());
        model.addAttribute("tasks",taskService.findAll());

        return "/task/update";
    }

    @PostMapping("/update/{id}")
    public String updateTask(@Valid @ModelAttribute("task") TaskDTO task, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            model.addAttribute("projects",projectService.findAll());
            model.addAttribute("employees",userService.findEmployees());
            model.addAttribute("tasks",taskService.findAll());
            return "/task/update";
        }

        taskService.update(task);

        return "redirect:/task/create";
    }


    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id){

        taskService.deleteById(id);

        return "redirect:/task/create";
    }


    @GetMapping("/employee/pending-tasks")
    public String getEmployeePendingTasks(Model model){

        model.addAttribute("tasks",taskService.findTasksByStatusIsNot(Status.COMPLETE));

        return "/task/pending-tasks";
    }

    @GetMapping("/employee/edit/{id}")
    public String employeeEditTask(@PathVariable Long id, Model model){

        model.addAttribute("task",taskService.findById(id));
        model.addAttribute("statuses",Status.values());
        model.addAttribute("tasks",taskService.findTasksByStatusIsNot(Status.COMPLETE));

        return "/task/status-update";
    }

    @PostMapping("/employee/update/{id}")
    public String employeeUpdateTask(@Valid @ModelAttribute("task") TaskDTO task, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){

            model.addAttribute("statuses",Status.values());
            model.addAttribute("tasks",taskService.findTasksByStatusIsNot(Status.COMPLETE));

            return "/task/status-update";
        }

        taskService.updateStatus(task);

        return "redirect:/task/employee/pending-tasks";
    }

    @GetMapping("/employee/archive")
    public String employeeArchiveTasks(Model model){

        model.addAttribute("tasks",taskService.findTasksByStatus(Status.COMPLETE));

        return "/task/archive";
    }

}
