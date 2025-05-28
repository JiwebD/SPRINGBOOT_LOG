package com.example.demo.controller;

import com.example.demo.domain.Service.ProjectService;
import com.example.demo.domain.dto.ProjectDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback(false)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjectService projectService;  //  실제 서비스 빈 사용

    @DisplayName("GET /project/upload 테스트")
    @Test
    public void testUploadForm() throws Exception {
        mockMvc.perform(get("/project/upload"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("POST /project/upload 테스트")
    @Test
    public void testUpload() throws Exception {
        // given
        MockMultipartFile file = new MockMultipartFile(
                "thumbnail",
                "sample.jpg",
                "image/jpeg",
                "image-content".getBytes()
        );

        // when & then
        mockMvc.perform(multipart("/project/upload")
                        .file(file)
                        .param("projectTitle", "테스트 프로젝트")
                        .param("projectInfo", "설명입니다."))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/read"))
                .andDo(print());
    }

    @DisplayName("GET /project/read 테스트")
    @Test
    public void testRead() throws Exception {
        mockMvc.perform(get("/project/read"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("GET /project/delete 테스트")
    @Test
    public void testDelete() throws Exception {
        // 삭제하려는 projectId는 실제 DB에 존재해야 함
        mockMvc.perform(get("/project/delete")
                        .param("projectId", "8"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/read"))
                .andDo(print());
    }

    @DisplayName("GET /project/update 테스트")
    @Test
    public void testUpdateForm() throws Exception {
        // 업데이트 폼 대상 projectId는 존재해야 함
        mockMvc.perform(get("/project/update")
                        .param("projectId", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("POST /project/update 테스트")
    @Test
    public void testUpdate() throws Exception {
        // given
        MockMultipartFile file = new MockMultipartFile(
                "thumbnail",
                "new.jpg",
                "image/jpeg",
                "new content".getBytes()
        );

        // when & then
        mockMvc.perform(multipart("/project/update")
                        .file(file)
                        .param("projectId", "8")
                        .param("projectTitle", "변경된 제목")
                        .param("projectInfo", "변경된 설명")
                        .param("existingImagePath", "upload/20240527/old.jpg")
                        .with(request -> {
                            request.setMethod("POST");  // multipart 기본은 POST지만 명시
                            return request;
                        }))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/read"))
                .andDo(print());
    }
}
