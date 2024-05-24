package com.cydeo.service.impl;

import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Task;
import com.cydeo.enums.Status;
import com.cydeo.mapper.TaskMapper;
import com.cydeo.repository.TaskRepository;
import com.cydeo.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
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
        Task entity = taskRepository.findById(dto.getId()).get();
        Task convertedDto = taskMapper.convertToEntity(dto);
        convertedDto.setId(entity.getId());
        convertedDto.setAssignedDate(entity.getAssignedDate());
        convertedDto.setStatus(Status.OPEN);
        taskRepository.save(convertedDto);
    }

    @Override
    public void deleteById(Long id) {

    }
}
