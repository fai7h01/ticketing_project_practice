package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final TaskService taskService;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, UserService userService, UserMapper userMapper, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.taskService = taskService;
    }


    @Override
    public List<ProjectDTO> listAllProjects() {
        return projectRepository.findAll().stream()
                .map(projectMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDTO findByProjectCode(String code) {
        return projectMapper.convertToDto(projectRepository.findByProjectCode(code));
    }

    @Override
    public void save(ProjectDTO dto) {
        if (dto.getStatus() == null) {
            dto.setStatus(Status.OPEN);
        }
        projectRepository.save(projectMapper.convertToEntity(dto));
    }

    @Override
    public void update(ProjectDTO dto) {
        if (dto.getStatus() == null)
            dto.setStatus(Status.OPEN);
        Project project = projectRepository.findByProjectCode(dto.getProjectCode());
        Project convertedDto = projectMapper.convertToEntity(dto);
        convertedDto.setId(project.getId());
        projectRepository.save(convertedDto);
    }

    @Override
    public void delete(String projectCode) {
        Project project = projectRepository.findByProjectCode(projectCode);
        project.setDeleted(true);
        project.setProjectCode(project.getProjectCode() + "-" + project.getId());
        taskService.deleteAllByProject(projectMapper.convertToDto(project));
        projectRepository.save(project);
    }

    @Override
    public void complete(String code) {
        Project project = projectRepository.findByProjectCode(code);
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);
        taskService.completeAllByProject(projectMapper.convertToDto(project));
    }

    @Override
    public List<ProjectDTO> listAllProjectDetails() {
        User manager = userMapper.convertToEntity(userService.findByUserName("harold@manager.com"));
        List<Project> projects = projectRepository.findByAssignedManager(manager);

        return projects.stream()
                .map(project -> {

                    ProjectDTO dto = projectMapper.convertToDto(project);
                    dto.setUnfinishedTask(taskService.findUnfinishedTaskCount(dto));
                    dto.setCompletedTask(taskService.findCompletedTaskCount(dto));

                    return dto;
                }).collect(Collectors.toList());
    }
}
