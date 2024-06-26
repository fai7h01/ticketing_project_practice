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
@Table(name = "projects")
@SQLRestriction("is_deleted=false")
public class Project extends BaseEntity{

    private String projectCode;
    private String projectName;
    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private Status projectStatus;
    private String projectDetail;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User assignedManager;
}
