package com.numble.domain.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoVO {

    private String userId;
    private String password;
    private int usages;
    private String deleteYn;
}
