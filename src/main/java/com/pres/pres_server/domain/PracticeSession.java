package com.pres.pres_server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDateTime;

@Entity
@Table(name="PracticeSession")
@Getter
@Setter
public class PracticeSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;
    private String audioUrl;
    private String sttText;

    private LocalDateTime practicedAt;
    private Time duration; //타입 확인

    //일대다 속성 타입은 컨테이너여야 합니다. ??
    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project; //FK, project_id

}
