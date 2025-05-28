package com.example.demo.domain.mapper;

import com.example.demo.domain.dto.ProjectDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLOutput;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProjectMapperTest {

    @Autowired
    private ProjectMapper projectMapper;

    @Test
    public void insertProjectTest() {
        ProjectDto dto = ProjectDto.builder()
                .projectTitle("삭제 테스트 프로젝트")
                .projectInfo("이건 삭제용 테스트입니다.")
                .imagePath("test/path/delete.jpg")
                .build();

        projectMapper.insertProject(dto);

        assertThat(dto.getProjectId()).isNotNull();  // auto_increment 확인
        System.out.println("삽입된 projectId: " + dto.getProjectId());
    }

    @Test
    public void selectAllTest() {
        List<ProjectDto> list = projectMapper.selectAll();
        assertThat(list).isNotEmpty();
        list.forEach(System.out::println);
    }

    @Test
    public void selectOneTest() {
        ProjectDto dto = projectMapper.selectOne(1);
        assertThat(dto).isNotNull();
        System.out.println(dto);
    }

    @Test
    public void updateProjectTest() {
        ProjectDto dto = ProjectDto.builder()
                .projectId(1)
                .projectTitle("수정된 제목")
                .projectInfo("수정된 정보")
                .imagePath("upload/changed.jpg")
                .build();

        projectMapper.updateProject(dto);
    }


    @Test
    @DisplayName("Project 삭제 테스트 및 메시지 확인")
    void deleteProjectTest() {
        // given: 테스트용 프로젝트 저장
        ProjectDto dto = ProjectDto.builder()
                .projectTitle("삭제 테스트 프로젝트")
                .projectInfo("이건 삭제용 테스트입니다.")
                .imagePath("test/path/delete.jpg")
                .build();

        projectMapper.insertProject(dto);
        int projectId = dto.getProjectId();
        System.out.println(" 저장된 projectId: " + projectId);

        // when: 삭제 수행
        projectMapper.deleteProject(projectId);
        System.out.println(" 삭제가 완료되었습니다. (projectId: " + projectId + ")");

        // then: 삭제 후 null이어야 함
        ProjectDto result = projectMapper.selectOne(projectId);
        assertThat(result).isNull();
    }
}
