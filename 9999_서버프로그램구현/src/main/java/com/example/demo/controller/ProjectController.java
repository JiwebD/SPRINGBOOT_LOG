package com.example.demo.controller;

import com.example.demo.domain.Service.ProjectService;
import com.example.demo.domain.dto.ProjectDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Controller
@Slf4j
@RequestMapping("/project")
public class ProjectController {

    private String ROOT_PATH = "c:\\";  //루트 경로
    private String UPLOAD_PATH = "upload"; //업로드 경로

    @Autowired
    private ProjectService projectService;

    @GetMapping("/upload")
    public void project_get() {
        log.info("GET /project/upload...");
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("thumbnail") MultipartFile file,
                         @RequestParam("projectTitle") String title,
                         @RequestParam("projectInfo") String info) throws Exception {

        LocalDateTime now = LocalDateTime.now();
        // yyyyMMdd_HHmmss 폴더명
        String folderName = now.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        String UploadPath = ROOT_PATH
                + File.separator // \\구분자
                + UPLOAD_PATH
                + File.separator
                + folderName
                + File.separator;

        File dir = new File(UploadPath);
        if (!dir.exists()) dir.mkdirs();

        String saveName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        File saveFile = new File(UploadPath, saveName);
        file.transferTo(saveFile);

        // DB에 저장할 상대 경로
        String dbPath = UPLOAD_PATH + "/" + folderName + "/" + saveName;

        ProjectDto dto = new ProjectDto();
        dto.setProjectTitle(title);
        dto.setProjectInfo(info);
        dto.setImagePath(dbPath);

        projectService.saveProject(dto);
        // 로그로 id 확인
        System.out.println("📌 생성된 projectId = " + dto.getProjectId());

        return "redirect:/project/read";
    }

    @GetMapping("/read")
    public String read(Model model) {
        List<ProjectDto> list = projectService.findAll();
        model.addAttribute("list", list);
        return "project/read"; // → /WEB-INF/views/project/read.jsp
    }
}
