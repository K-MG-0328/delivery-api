package com.github.mingyu.fooddeliveryapi.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {

    public User(String userId, EncodedPassword password, String email, UserInfo userInfo, UserRole userRole, UserStatus userStatus) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.userInfo = userInfo;
        this.role = userRole;
        this.status = userStatus;
    }

    @Id
    private String userId;

    @Embedded
    private EncodedPassword password;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Embedded
    private UserInfo userInfo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 255)
    private UserStatus status;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedDate = LocalDateTime.now();
    }

    public void changePassword(EncodedPassword password) {
        this.password = password;
    }

    public void changeStatus(UserStatus status) {
        this.status = status;
    }
}
