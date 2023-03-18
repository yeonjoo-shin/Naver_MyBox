package com.numble.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCode {

    USER_DB_ERROR( 502,"USER-ERR-502","유저 DB 에러"),

    //Success Status
    SUCCESS_INSERT(200, "", "등록 성공 하였습니다."),
    SUCCESS_UPDATE(200, "", "수정 성공 하였습니다."),
    SUCCESS_DELETE(200, "", "삭제 성공 하였습니다."),
    SUCCESS_SELECT(200, "", "조회 성공 하였습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;

}
