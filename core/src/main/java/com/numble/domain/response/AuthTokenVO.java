package com.numble.domain.response;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokenVO {
    private String grantType;
    private String accessToken;
    private long accessTokenExpiresIn;
    private String refreshToken;
    private String accessName;
    private int authority;
}
