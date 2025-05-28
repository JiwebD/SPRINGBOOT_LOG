package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDto {
    private int projectId;
    private String projectTitle;
    private String projectInfo;
    private String imagePath;

    public String getFormattedId() {
        return String.format("%06d", projectId);  // 6자리 0패딩
    }
    public String getShortInfo() {
        if (projectInfo == null) return "";
        return projectInfo.length() > 15 ? projectInfo.substring(0, 15) + "..." : projectInfo;
    }

}
