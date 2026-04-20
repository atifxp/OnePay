package com.cts.OnePay.user.repository;

import com.cts.OnePay.user.dto.userDtos.UserResponseDto;
import com.cts.OnePay.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("SELECT new com.cts.OnePay.user.dto.UserResponseDto(u.userId,u.fullName,u.email,u.phone, u.role, u.accountStatus) from User u WHERE u.userId = :userId")
    public UserResponseDto findByUserIdExceptPassword(@Param("userId") Long userId);
}
