package com.project.sns.service;

import com.project.sns.exception.ErrorCode;
import com.project.sns.exception.SnsApplicationException;
import com.project.sns.model.User;
import com.project.sns.model.entity.UserEntity;
import com.project.sns.repository.UserEntityRepository;
import com.project.sns.service.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserEntityRepository userEntityRepository;
    private final BCryptPasswordEncoder encoder;

    private static String secretKey="project.sns-application-2023.secret_key";
    private static Long expiredTimeMs= 2592000000L;

    @Transactional
    public User join(String username, String password) {
        // 회원가입하려는 username으로 회원가입된 user가 있는지
        userEntityRepository.findByUsername(username).ifPresent( it -> {
           throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME,String.format("%s is duplicated",username));
        });

        // 회원가입 진행 = user를 등록
        return User.fromEntity(userEntityRepository.save(UserEntity.of(username, encoder.encode(password))));
    }

    // TODO: implements
    public String login(String username, String password) {
        // 회원가입 여부 체크
        UserEntity userEntity = userEntityRepository.findByUsername(username).orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s is not found",username)));

        // 비밀번호 체크
        if(!encoder.matches(password,userEntity.getPassword())){
            throw new SnsApplicationException(ErrorCode.INVALID_PASSWORD);
        }

        // 토큰 생성
        String token = JwtTokenUtils.generateToken(username, secretKey, expiredTimeMs);

        return token;
    }
}
