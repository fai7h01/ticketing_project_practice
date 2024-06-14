package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Task;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.TaskMapper;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.TaskRepository;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ProjectMapper projectMapper;
    private final UserMapper userMapper;
    private final UserService userService;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, ProjectMapper projectMapper, UserMapper userMapper, UserService userService) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.projectMapper = projectMapper;
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @Override
    public List<TaskDTO> listAllTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::convertTODto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO findById(Long id) {
        return taskMapper.convertTODto(taskRepository.findById(id).get());
    }

    @Override
    public void save(TaskDTO dto) {
        dto.setStatus(Status.OPEN);
        dto.setAssignedDate(LocalDate.now());
        taskRepository.save(taskMapper.convertToEntity(dto));
    }

    @Override
    public void update(TaskDTO dto) {
        Optional<Task> foundTask = taskRepository.findById(dto.getId());
        Task convertedDto = taskMapper.convertToEntity(dto);
        if (foundTask.isPresent()){
            convertedDto.setTaskStatus(dto.getStatus() == null ? foundTask.get().getTaskStatus() : dto.getStatus());
            convertedDto.setAssignedDate(foundTask.get().getAssignedDate());
            taskRepository.save(convertedDto);
        }
    }

    @Override
    public void deleteById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()){
            task.get().setIsDeleted(true);
            taskRepository.save(task.get());
        }
    }

    @Override
    public int findCompletedTaskCount(ProjectDTO projectDTO) {
        return taskRepository.completedTasksCount(projectMapper.convertToEntity(projectDTO));
    }

    @Override
    public int findUnfinishedTaskCount(ProjectDTO projectDTO) {
        return taskRepository.unfinishedTasksCount(projectMapper.convertToEntity(projectDTO));
    }

    @Override
    public List<TaskDTO> listAllByStatusIsNot(Status status) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDTO loggedInUser = userService.findByUserName(username);

        return taskRepository.findAllByTaskStatusIsNotAndAssignedEmployee(status,userMapper.convertToEntity(loggedInUser)).stream()
                .map(taskMapper::convertTODto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> listAllByStatus(Status status) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDTO loggedInUser = userService.findByUserName(username);

        return taskRepository.findAllByTaskStatusIsAndAssignedEmployee(status,userMapper.convertToEntity(loggedInUser)).stream()
                .map(taskMapper::convertTODto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllByProject(ProjectDTO dto) {
        List<Task> tasks = taskRepository
                .findAllByProject(projectMapper.convertToEntity(dto));
        tasks.forEach(task -> deleteById(task.getId()));
    }

    @Override
    public void completeAllByProject(ProjectDTO dto) {
        List<Task> tasks = taskRepository
                .findAllByProject(projectMapper.convertToEntity(dto));
        tasks.stream().map(taskMapper::convertTODto)
                .forEach(taskDTO -> {
                    taskDTO.setStatus(Status.COMPLETE);
                    update(taskDTO);
                });
    }

    @Override
    public List<TaskDTO> listAllNonCompletedByAssignedEmployee(UserDTO employee) {
        List<Task> tasks = taskRepository.findAllByTaskStatusIsNotAndAssignedEmployee(Status.COMPLETE, userMapper.convertToEntity(employee));
        return tasks.stream()
                .map(taskMapper::convertTODto)
                .collect(Collectors.toList());
    }

}
