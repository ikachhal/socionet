package com.adwicorp.aanandamsn.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "adm_users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message = "User name Id must not be empty")
    @Column(nullable = false, unique = true)
    private String userNameId;

    private String fullName;

    @NotBlank(message = "Email must not be empty")
    @Column(nullable = false, unique = true)
    private String email;

    private String imagePath;

    @Column(updatable = false)
    private LocalDateTime createdOn;

    @Column(insertable = false, updatable = false)
    private LocalDateTime updatedOn;

    private boolean deleted;

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
                ", isDeleted=" + deleted +
                '}';
    }
}
