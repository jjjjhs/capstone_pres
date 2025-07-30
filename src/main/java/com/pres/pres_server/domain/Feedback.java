package com.pres.pres_server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="Feedback")
@Getter
@Setter
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;

    private int spmScore;
    private int fillerScore;
    private int repeatScore;

    private int totalScore;

    private String grade;

    @OneToOne
    @JoinColumn(name="session_id")
    private PracticeSession practiceSession;
}
