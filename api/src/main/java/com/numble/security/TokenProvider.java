package com.numble.security;

import com.numble.domain.request.TokenReq;
import com.numble.domain.response.AuthTokenVO;
import com.numble.domain.response.BusinessException;
import com.numble.domain.response.StatusCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import java.security.Key;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";
    private static final String ACCESS_TOKEN_NAME = "access";
    private static final String REFRESH_TOKEN_NAME = "refresh";
    private static final String EXCEPTION_NAME = "exception";
    private static final String UNSUPPRORTED_NAME = "Unsupported";

    private Key key;
    @Value("${jwt.secret}")
    private String secretKey;

    public Date getTokenTime(String tokenType, Boolean userType) {
        if (Boolean.TRUE.equals(userType)) {
            // user type
            return tokenType.equals(ACCESS_TOKEN_NAME) ? Timestamp.valueOf(LocalDateTime.now().plusYears(5))
                    : Timestamp.valueOf(LocalDateTime.now().plusYears(10));
        } else {
            // p token and web user type
            return Timestamp.valueOf(LocalDateTime.now().plusDays(7));
        }
    }

    public AuthTokenVO generateTokenDto(Authentication authentication, int userAuth, String type, TokenReq token) {
        //AccessToken 생성
        Date accessTokenExpiresIn =
                userAuth == 1 ? getTokenTime(ACCESS_TOKEN_NAME, true) : getTokenTime(ACCESS_TOKEN_NAME, false);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, userAuth)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        //RefreshToken 생성
        String refreshToken = Jwts.builder()
                .setExpiration(
                        userAuth == 1 ? getTokenTime(REFRESH_TOKEN_NAME, true) : getTokenTime(REFRESH_TOKEN_NAME, false))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        if ("reissue".equals(type)) {
            refreshToken = token.getRefreshToken();
        }

        return AuthTokenVO.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .build();
    }

    public Authentication getAuthentication(String accessToken) {
        //토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new BusinessException(StatusCode.INVALID_TOKEN);
        }

        //클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principle = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principle, "", authorities);
    }

    public boolean validateToken(String token, ServletRequest request) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            if (request != null) {
                request.setAttribute(EXCEPTION_NAME, UNSUPPRORTED_NAME);
            }
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            if (request != null) {
                request.setAttribute(EXCEPTION_NAME, "Expired");
            }
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            if (request != null) {
                request.setAttribute(EXCEPTION_NAME, UNSUPPRORTED_NAME);
            }
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            if (request != null) {
                request.setAttribute(EXCEPTION_NAME, UNSUPPRORTED_NAME);
            }
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBites = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBites);
    }
}
