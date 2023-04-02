package com.project.sns.service;

import com.project.sns.exception.ErrorCode;
import com.project.sns.exception.SnsApplicationException;
import com.project.sns.model.User;
import com.project.sns.model.entity.UserEntity;
import com.project.sns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserEntityRepository userEntityRepository;

    // TODO: implements
    public User join(String username, String password) {
        // 회원가입하려는 username으로 회원가입된 user가 있는지
        userEntityRepository.findByUsername(username).ifPresent( it -> {
           throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME,String.format("%s is duplicated",username));
        });

        // 회원가입 진행 = user를 등록

        return User.fromEntity(userEntityRepository.save(UserEntity.of(username, password)));
    }

    // TODO: implements
    public String login(String username, String password) {
        // 회원가입 여부 체크
        UserEntity userEntity = userEntityRepository.findByUsername(username).orElseThrow(() -> new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME,""));

        // 비밀번호 체크
        if(!userEntity.getPassword().equals(password)){
            throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME,"");
        }

        // 토큰 생성

        return "";
    }
}
