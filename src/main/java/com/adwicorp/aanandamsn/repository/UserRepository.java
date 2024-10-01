package com.adwicorp.aanandamsn.repository;

import com.adwicorp.aanandamsn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByIsDeletedFalse();
    User findByUserId(Long userId);
}
