package com.pres.pres_server.domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.w3c.dom.Text;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false)
    private boolean emailVerified = false;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt; //= LocalDateTime.now();
    @Column(nullable = false)
    private boolean pushEnabled;

    @Column(unique = true)
    private String kakaoId; //타입 확인 필요
    @Column
    private String kakaoToken;
    @Column
    private LocalDateTime tokenExpiresAt;

//    @OneToMany(mappedBy = "user")
//    private List<TeamMember> teamMembers;
//    @OneToMany(mappedBy = "user")
//    private List<WorkSpace> workSpace;
}
