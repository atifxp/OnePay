package com.cts.OnePay.user.service;

import com.cts.OnePay.user.dto.userDtos.UserResponseDto;
import com.cts.OnePay.user.dto.userDtos.UserUpdateRequestDto;
import com.cts.OnePay.user.model.User;
import com.cts.OnePay.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public UserResponseDto getProfile(Long userId){
        UserResponseDto responseDto = userRepository.findByUserIdExceptPassword(userId);

        if(responseDto == null)
            throw new EntityNotFoundException("User does not Exist");

        return responseDto;
    }

    @Transactional(rollbackFor = Exception.class)
    public UserResponseDto updateProfile(Long userId, UserUpdateRequestDto dto){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User does not Exist"));

        if (dto.getFullName() != null) user.setFullName(dto.getFullName());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getPhone() != null) user.setPhone(dto.getPhone());

        //auto saved

        return modelMapper.map(user, UserResponseDto.class);
    }


}
