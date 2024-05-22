package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.Project;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
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
        if (dto.getStatus() == null){
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
    public void delete(ProjectDTO dto) {

    }
}
