package com.sanskar.project.stelloom.user;

import com.sanskar.project.stelloom.common.exception.ResourseNotFoundExecption;
import com.sanskar.project.stelloom.user.dto.UserRequest;
import com.sanskar.project.stelloom.user.dto.UserResponse;
import com.sanskar.project.stelloom.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private  final UserRepository userRepository;
    private  final UserMapper userMapper;

    public UserResponse updateProfile(Long orgId,Long id, UserRequest request){
        User user=userRepository.findByOrganizationIdAndId(orgId,id).orElseThrow(()->new ResourseNotFoundExecption("User Not Found"));
        user.setUsername(request.fullName());
        User res=userRepository.save(user);
        return userMapper.toResponse(res);
    }

    public UserResponse getUserProfile(String username){
        User user=userRepository.findByUsername(username).orElseThrow(()->new ResourseNotFoundExecption("User Not Found"));
        return userMapper.toResponse(user);
    }
}
