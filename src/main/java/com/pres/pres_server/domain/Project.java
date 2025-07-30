package com.pres.pres_server.domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
//클래스 import 어케 하더라

@Entity
@Table(name = "Project")
@Getter
@Setter

public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;
    private String title;
    //private String description; 필요할까?

    private LocalDateTime createdAt; //=LocalDateTime.now();
    private LocalDateTime dueDate;
    private String category;
    private boolean isBookmarked;

    @ManyToOne
    @JoinColumn(name="workspace_id")
    private WorkSpace workSpace; //FK, workspace_id

}
