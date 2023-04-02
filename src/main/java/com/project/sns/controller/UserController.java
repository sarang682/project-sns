package com.project.sns.controller;

import com.project.sns.controller.request.UserJoinRequest;
import com.project.sns.controller.response.Response;
import com.project.sns.controller.response.UserJoinResponse;
import com.project.sns.model.User;
import com.project.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    // TODO: implements
    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request){
        User user = userService.join(request.getUsername(), request.getPassword());
        return Response.success(UserJoinResponse.fromUser(user));
    }

}
