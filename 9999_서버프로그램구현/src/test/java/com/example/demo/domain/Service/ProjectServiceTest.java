package com.example.demo.domain.Service;

import com.example.demo.domain.dto.ProjectDto;
import com.example.demo.domain.mapper.ProjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProjectServiceTest {

    @Mock
    private ProjectMapper projectMapper;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("프로젝트 저장 테스트")
    void testSaveProject() {
        ProjectDto dto = new ProjectDto();
        dto.setProjectTitle("테스트 제목");
        dto.setProjectInfo("테스트 설명");

        doNothing().when(projectMapper).insertProject(dto);

        projectService.saveProject(dto);

        verify(projectMapper, times(1)).insertProject(dto);
    }

    @Test
    @DisplayName("전체 조회 테스트")
    void testFindAll() {
        when(projectMapper.selectAll()).thenReturn(List.of(
                new ProjectDto(1, "title1", "info1", "img1"),
                new ProjectDto(2, "title2", "info2", "img2")
        ));

        // when
        List<ProjectDto> result = projectService.findAll();

        // then
        System.out.println("전체 조회 결과:");
        result.forEach(System.out::println);

        assertThat(result).hasSize(2);
        verify(projectMapper, times(1)).selectAll();
    }

    @Test
    @DisplayName("단건 조회 테스트")
    void testFindOne() {
        ProjectDto dummy = new ProjectDto(1, "title", "info", "img");

        when(projectMapper.selectOne(1)).thenReturn(dummy);

        ProjectDto result = projectService.findOne(1);

        assertThat(result.getProjectId()).isEqualTo(1);
        assertThat(result.getProjectTitle()).isEqualTo("title");
    }

    @Test
    @DisplayName("projectId로 프로젝트 삭제 테스트")
    void testDeleteProject() {
        int projectIdToDelete = 9; // 실제 DB에 존재하는 ID로 테스트

        try {
            projectService.delete(projectIdToDelete);
            System.out.println("삭제 완료: projectId = " + projectIdToDelete);
        } catch (Exception e) {
            System.out.println("삭제 실패: " + e.getMessage());
            throw e; // 테스트 실패로 처리
        }
    }

    @Test
    @DisplayName("프로젝트 수정 테스트")
    void testUpdate() {
        ProjectDto dto = new ProjectDto(1, "수정된 제목", "수정된 설명", "수정된 이미지");

        doNothing().when(projectMapper).updateProject(dto);

        projectService.update(dto);

        verify(projectMapper, times(1)).updateProject(dto);
    }
}
