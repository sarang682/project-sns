package com.project.sns.service;
import com.project.sns.exception.SnsApplicationException;
import com.project.sns.fixture.UserEntityFixture;
import com.project.sns.model.entity.UserEntity;
import com.project.sns.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Autowired private UserService userService;

    @MockBean private UserEntityRepository userEntityRepository;

    @MockBean private BCryptPasswordEncoder encoder;

    @Test
    void 회원가입이_정상적으로_동작하는_경우() {
        String username="username";
        String password="password";

        // mocking
        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(encoder.encode(password)).thenReturn("encrypt_password");
        when(userEntityRepository.save(any())).thenReturn(Optional.of(UserEntityFixture.get(username, password)));

        Assertions.assertDoesNotThrow(() -> userService.join(username, password));
    }

    @Test
    void 회원가입시_username으로_회원가입한_유저가_이미_있는_경우() {
        String username="username";
        String password="password";

        UserEntity fixture = UserEntityFixture.get(username, password);

        // mocking
        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(fixture));
        when(encoder.encode(password)).thenReturn("encrypt_password");
        when(userEntityRepository.save(any())).thenReturn(Optional.of(fixture));

        Assertions.assertThrows(SnsApplicationException.class,()->userService.join(username,password));
    }

    @Test
    void 로그인이_정상적으로_동작하는_경우() {
        String username="username";
        String password="password";

        UserEntity fixture = UserEntityFixture.get(username, password);

        // mocking
        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(fixture));

        Assertions.assertDoesNotThrow(() -> userService.login(username, password));
    }

    @Test
    void 로그인시_username으로_회원가입한_유저가_없는_경우() {
        String username="username";
        String password="password";

        // mocking
        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.empty());

        Assertions.assertThrows(SnsApplicationException.class,()->userService.login(username,password));
    }

    @Test
    void 로그인시_패스워드가_틀린_경우() {
        String username="username";
        String password="password";
        String wrongPassword="wrongPassword";

        UserEntity fixture = UserEntityFixture.get(username, password);

        // mocking
        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(fixture));

        Assertions.assertThrows(SnsApplicationException.class,()->userService.login(username,wrongPassword));
    }

}