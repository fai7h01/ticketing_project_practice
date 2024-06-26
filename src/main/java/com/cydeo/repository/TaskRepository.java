package com.cydeo.repository;

import com.cydeo.entity.Project;
import com.cydeo.entity.Task;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("select count(t) from Task t where t.project = ?1 and t.taskStatus = 'COMPLETE'")
    int completedTasksCount(Project project);

    @Query("select count(t) from Task t where t.project = ?1 and t.taskStatus <> 'COMPLETE'")
    int unfinishedTasksCount(Project project);

    List<Task> findAllByProject(Project project);

    List<Task> findAllByTaskStatusIsNotAndAssignedEmployee(Status status, User assignedEmployee);
    List<Task> findAllByTaskStatusIsAndAssignedEmployee(Status status, User assignedEmployee);
}
