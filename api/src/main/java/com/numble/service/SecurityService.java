package com.numble.service;

import com.numble.domain.request.UserAuthReq;
import com.numble.domain.response.BusinessException;
import com.numble.domain.response.StatusCode;
import com.numble.lifecycle.MyBoxDataLifeCycle;
import com.numble.vo.AuthUserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityService {

    private final MyBoxDataLifeCycle lifeCycle;

    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public void signUp(UserAuthReq userAuthReq) {
        if (getApiUser(userAuthReq.getUserName()) == null) {
            userAuthReq.setPassword(passwordEncoder.encode(userAuthReq.getPassword()));
        } else {
            throw new BusinessException(StatusCode.USER_DB_ERROR);
        }

    }

    public AuthUserVO getApiUser(String userName) {
        return lifeCycle.getApiUser(userName);
    }

    public int registerUser(UserAuthReq userAuthReq) {
        return lifeCycle.registerUser(userAuthReq);
    }
}
