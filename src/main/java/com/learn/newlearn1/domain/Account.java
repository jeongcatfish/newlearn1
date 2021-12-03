package com.learn.newlearn1.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Account {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    private String password;

    private boolean emailVerified;

    private String emailCheckToken; //이메일 검증시 사용

    private LocalDateTime joinedAt;

    private String bio; //간단한 소개

    private String url;

    private String occupation;

    private String location;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String profileImage;

    //알림
    private boolean studyCreatedByEmail;
    private boolean studyCreatedByWeb;

    private boolean studyEnrollmentResultByEmail;
    private boolean studyEnrollmentResultByWeb;

    private boolean studyUpdatedByEmail;
    private boolean studyUpdatedByWeb;

    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
    }

    public void completeSignUp() {
        this.setEmailVerified(true);
        this.setJoinedAt(LocalDateTime.now());
    }
}
