package com.adwicorp.aanandamsn.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "adm_users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userNameId;

    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    private String imagePath;

    @Column(name = "created_on", updatable = false)
    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private boolean isDeleted;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userNameId='" + userNameId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
