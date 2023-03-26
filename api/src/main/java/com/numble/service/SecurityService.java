package com.numble.service;

import com.numble.domain.request.UserAuthReq;
import com.numble.domain.response.AuthTokenVO;
import com.numble.domain.response.BusinessException;
import com.numble.domain.response.StatusCode;
import com.numble.domain.response.UserInfoVO;
import com.numble.lifecycle.MyBoxDataLifeCycle;
import com.numble.security.TokenProvider;
import com.numble.vo.AuthUserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityService {

    private final MyBoxDataLifeCycle lifeCycle;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final TokenProvider tokenProvider;

    // 회원가입
    public void signUp(UserAuthReq userAuthReq) {
        if (getApiUser(userAuthReq.getUserName()) == null) {
            userAuthReq.setPassword(passwordEncoder.encode(userAuthReq.getPassword()));
            if (registerUser(userAuthReq) == 0) {
                throw new BusinessException(StatusCode.USER_DB_ERROR);
            }
        } else {
            throw new BusinessException(StatusCode.USER_DB_ERROR);
        }
    }

    public AuthTokenVO login(UserAuthReq userAuthReq) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userAuthReq.getUserName(),userAuthReq.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);

        // 임시 권한
        int  auth = 0;

        AuthTokenVO authTokenVO = tokenProvider.generateTokenDto(authentication,auth,"login",null);
        AuthUserVO vo = getApiUser(userAuthReq.getUserName());
        if (vo.getUserName() != null) {
            authTokenVO.setAccessName(vo.getUserName());
            authTokenVO.setAuthority(auth);
        }

        return authTokenVO;
    }
    // 로그인 usercheck
    public AuthUserVO getApiUser(String userId) {
        return lifeCycle.getApiUser(userId);
    }

    // user 등록
    public int registerUser(UserAuthReq userAuthReq) {
        return lifeCycle.registerUser(userAuthReq);
    }

    public UserInfoVO getUserDetailInfo(String userId) {
        return lifeCycle.getUserDetailInfo(userId);
    }
}
