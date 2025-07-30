package com.pres.pres_server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="PresentationFile")
@Getter
@Setter

public class PresentationFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    private String fileUrl;
    //private String file_name;
    private String extractedText;

    private LocalDateTime uploadedAt;

    @OneToOne
    @JoinColumn(name="projectId")
    private Project project; //FK, project_id
}
