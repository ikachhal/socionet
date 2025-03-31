package com.adwicorp.aanandamsn.repository;

import com.adwicorp.aanandamsn.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByDeletedFalse();
    UserEntity findByUserId(Long userId);
}
