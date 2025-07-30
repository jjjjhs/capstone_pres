package com.pres.pres_server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.security.PrivateKey;
import java.time.LocalDateTime;

@Entity
@Table(name = "WorkSpace")
@Getter
@Setter

public class WorkSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workspaceId;
    private String workspaceName;
    //private String workspace_description;
    private LocalDateTime createdAt; //=LocalDateTime.now();


    //user id 받음
    @ManyToOne
    @JoinColumn(name="userId")
    private User user; //FK, user_id

    //project에서 w.s id받음
    //teammember에서 w.s id받음

}
