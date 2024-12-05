package com.adwicorp.aanandamsn;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import com.adwicorp.aanandamsn.exception.BusinessException;
import com.adwicorp.aanandamsn.mapper.UserMapper;
import com.adwicorp.aanandamsn.model.entity.UserEntity;
import com.adwicorp.aanandamsn.model.response.UserResponse;
import com.adwicorp.aanandamsn.repository.UserRepository;
import com.adwicorp.aanandamsn.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getAllUsers_ShouldReturnUserResponses_WhenUsersExist() {
        when(userRepository.findByDeletedFalse()).thenReturn(getUserEntityList());
        when(userMapper.mapToUserResponse(any())).thenReturn(getUserResponseWhenUserExist());

        // Act
        List<UserResponse> result = userService.getAllUsers();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFullName()).isEqualTo("John Doe");

        verify(userRepository, times(1)).findByDeletedFalse();
        verify(userMapper, times(1)).mapToUserResponse(getUserEntity());
    }

    @Test
    void getAllUsers_ShouldReturnEmptyList_WhenNoUsersExist() {
        // Arrange
        when(userRepository.findByDeletedFalse()).thenReturn(Collections.emptyList());

        // Act
        List<UserResponse> result = userService.getAllUsers();

        // Assert
        assertThat(result).isEmpty();

        verify(userRepository, times(1)).findByDeletedFalse();
        verify(userMapper, times(0)).mapToUserResponse(any());
    }

    @Test
    void getAllUsers_ShouldThrowBusinessException_WhenAnExceptionOccurs() {
        // Arrange
        when(userRepository.findByDeletedFalse()).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        assertThatThrownBy(() -> userService.getAllUsers())
                .isInstanceOf(BusinessException.class)
                .hasMessage("Internal server error occurred"); // Customize this based on your error message

        verify(userRepository, times(1)).findByDeletedFalse();
        verify(userMapper, times(0)).mapToUserResponse(any());
    }

    private UserEntity getUserEntity() {
        return UserEntity.builder()
                .email("ina.kachhal@gmail.com")
                .fullName("John Doe")
                .userNameId("Ina@1234")
                .deleted(false)
                .imagePath("https://www.linkedin.com/in/ina-kachhal/")
                .userId(1L)
                .build();
    }

    private List<UserEntity> getUserEntityList() {
        return Arrays.asList(getUserEntity());
    }

    private UserResponse getUserResponseWhenUserExist() {
        return UserResponse.builder()
                .email("ina.kachhal@gmail.com")
                .fullName("John Doe")
                .userNameId("Ina@1234")
                .imagePath("https://www.linkedin.com/in/ina-kachhal/")
                .userId(1L)
                .build();

    }
}
