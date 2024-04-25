package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl extends AbstractMapService<ProjectDTO,String> implements ProjectService {

    private final TaskService taskService;

    public ProjectServiceImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public ProjectDTO save(ProjectDTO project) {
        if (project.getStatus() == null)
            project.setStatus(Status.OPEN);
        return super.save(project.getProjectCode(), project);
    }

    @Override
    public ProjectDTO findById(String projectCode) {
        return super.findById(projectCode);
    }

    @Override
    public List<ProjectDTO> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(String projectCode) {
        super.deleteById(projectCode);
    }

    @Override
    public void update(ProjectDTO project) {
        if (project.getStatus() == null)
            project.setStatus(findById(project.getProjectCode()).getStatus());
        super.update(project.getProjectCode(), project);
    }

    @Override
    public void complete(ProjectDTO project) {
        project.setStatus(Status.COMPLETE);
    }

    @Override
    public List<ProjectDTO> findCountedProjects(UserDTO manager) {

        List<ProjectDTO> projects = findAll().stream()
                .filter(project -> project.getAssignedManager().equals(manager))
                .map(project -> {

                    List<TaskDTO> taskList = taskService.findTaskByManager(manager);

                    int completedTaskCount = (int) taskList.stream()
                            .filter(task -> task.getProject().equals(project) && task.getStatus().equals(Status.COMPLETE))
                            .count();
                    int unfinishedTaskCount = (int) taskList.stream()
                            .filter(task -> task.getProject().equals(project) && !task.getStatus().equals(Status.COMPLETE))
                            .count();

                    project.setUnfinishedTask(unfinishedTaskCount);
                    project.setCompletedTask(completedTaskCount);

                    return project;
                }).toList();

        return projects;
    }


}
