package com.numble.controller;

import com.numble.domain.response.CommonResponse;
import com.numble.domain.response.StatusCode;
import com.numble.domain.request.UserAuthReq;
import com.numble.service.SecurityService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/user/*")
@RequiredArgsConstructor
public class AuthController {

    private final SecurityService securityService;


    @ApiOperation(value = "회원 가입")
    @PostMapping(value = "/join")
    public ResponseEntity<CommonResponse<Object>> registerUser(@RequestBody UserAuthReq userAuthReq) {
        securityService.signUp(userAuthReq);
        return ResponseEntity.ok().body(new CommonResponse<>(StatusCode.SUCCESS_INSERT));
    }

    @ApiOperation(value = "유저 정보"
            , notes = "유저 정보를 조회하여 제공합니다.")
    @GetMapping(value = "/userInfo",produces = "application/json")
    public ResponseEntity<CommonResponse<Object>> getUserInfo() {
        return ResponseEntity.ok().body(new CommonResponse<>( "서비스단", StatusCode.SUCCESS_SELECT));
    }



}
