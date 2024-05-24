package com.cydeo.entity;

import com.cydeo.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
@SQLRestriction("is_deleted=false")
public class Task extends BaseEntity{

    private String taskSubject;
    private String taskDetail;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(columnDefinition = "DATE")
    private LocalDate assignedDate;

    @ManyToOne
    private Project project;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User assignedManager;


}
