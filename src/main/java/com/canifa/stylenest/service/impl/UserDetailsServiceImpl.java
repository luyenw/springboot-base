package com.canifa.stylenest.service.impl;
import com.canifa.stylenest.entity.CustomUserDetails;
import com.canifa.stylenest.entity.User;
import com.canifa.stylenest.entity.dto.UserDto;
import com.canifa.stylenest.mapper.UserMapper;
import com.canifa.stylenest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(user);
    }

    public List<UserDto> getAll(){
        return userRepository.findAll().stream().map(user->userMapper.toUserDTO(user)).toList();
    }
}
