package com.cydeo.service;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.enums.Status;

import java.util.List;

public interface TaskService {

    List<TaskDTO> listAllTasks();

    TaskDTO findById(Long id);

    void save(TaskDTO dto);

    void update(TaskDTO dto);

    void deleteById(Long id);

    int findCompletedTaskCount(ProjectDTO projectDTO);

    int findUnfinishedTaskCount(ProjectDTO projectDTO);

    List<TaskDTO> listAllByStatusIsNot(Status status);

    List<TaskDTO> listAllByStatus(Status status);

    void deleteAllByProject(ProjectDTO dto);
}
