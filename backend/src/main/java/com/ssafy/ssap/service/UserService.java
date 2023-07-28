package com.ssafy.ssap.service;

import com.ssafy.ssap.domain.user.Authority;
import com.ssafy.ssap.domain.user.User;
import com.ssafy.ssap.dto.user.UserDto;
import com.ssafy.ssap.exception.DuplicateMemberException;
import com.ssafy.ssap.exception.NotFoundMemberException;
import com.ssafy.ssap.repository.UserRepository;
import com.ssafy.ssap.util.SecurityUtil;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserDto signup(UserDto userDto) {
//        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
//            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
//        }

        if (userRepository.findOneWithAuthoritiesByEmail(userDto.getEmail()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .name(userDto.getName())
                .nickname(userDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return UserDto.from(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String email) {
        return UserDto.from(userRepository.findOneWithAuthoritiesByEmail(email).orElse(null));
    }

    public boolean getUserWithEmail(String email) {
        if (userRepository.findOneWithAuthoritiesByEmail(email).orElse(null) != null) {
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() {
        return UserDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithAuthoritiesByEmail)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
    }

    /*
    * email로 id(pk)값 찾기
    * */
    public Long getUserIdByUsername(String email) {
        Optional<User> optionalUser  = userRepository.findOneWithAuthoritiesByEmail(email);
        return optionalUser.map(User::getId).orElse(null);
    }
}
