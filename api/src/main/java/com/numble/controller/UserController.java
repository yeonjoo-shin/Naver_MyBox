package com.numble.controller;

import com.numble.response.CommonResponse;
import com.numble.response.StatusCode;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/user/*")
@RequiredArgsConstructor
public class UserController {

    @ApiOperation(value = "유저 정보"
            , notes = "유저 정보를 조회하여 제공합니다.")
    @GetMapping(value = "/userInfo",produces = "application/json")
    public ResponseEntity<CommonResponse<Object>> getUserInfo() {
        return ResponseEntity.ok().body(new CommonResponse<>( "서비스단", StatusCode.SUCCESS_SELECT));
    }



}
