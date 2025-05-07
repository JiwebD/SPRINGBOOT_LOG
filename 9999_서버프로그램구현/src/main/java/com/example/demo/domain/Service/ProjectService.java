package com.example.demo.domain.Service;

import com.example.demo.domain.dto.ProjectDto;
import com.example.demo.domain.mapper.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    public void saveProject(ProjectDto dto) {
        projectMapper.insertProject(dto);
    }

    public List<ProjectDto> findAll() {
        return projectMapper.selectAll();
    }

}
