package com.cts.OnePay.user.service;

import com.cts.OnePay.user.dto.userDtos.UserPromoteRequestDto;
import com.cts.OnePay.user.dto.userDtos.UserResponseDto;
import com.cts.OnePay.user.dto.userDtos.UserUpdateRequestDto;
import com.cts.OnePay.user.model.User;
import com.cts.OnePay.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserResponseDto getById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException("User Not Found"));
        return modelMapper.map(user,UserResponseDto.class);
    }

    @Transactional(readOnly = true)
    public UserResponseDto getProfile(Long userId){
        User response = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User does not Exist"));

        return modelMapper.map(response, UserResponseDto.class);
    }

    @Transactional(rollbackFor = Exception.class)
    public UserResponseDto updateProfile(Long userId, UserUpdateRequestDto dto){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User does not Exist"));

        if (dto.getFullName() != null) user.setFullName(dto.getFullName());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getRole() != null) user.setRole(dto.getRole());

        //auto saved

        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> promoteUser(UserPromoteRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(()-> new EntityNotFoundException("User Not Found"));
        user.setRole(dto.getRole());
        return Map.of("message", "User "+user.getFullName()+" successfully promoted");
    }
}
