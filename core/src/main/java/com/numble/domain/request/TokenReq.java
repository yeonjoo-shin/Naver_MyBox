package com.numble.domain.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TokenReq {
    private String accessToken;
    private String refreshToken;
}
