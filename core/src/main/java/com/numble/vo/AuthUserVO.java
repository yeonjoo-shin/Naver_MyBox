package com.numble.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserVO {

    private String userName;
    private String password;
    private String checkPassword; // 패스워드 체크 용도
    private String currentPassword; // 현재 패스워드
    private String regDate;
}
