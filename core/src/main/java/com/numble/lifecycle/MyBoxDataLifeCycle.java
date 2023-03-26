package com.numble.lifecycle;

import com.numble.domain.request.UserAuthReq;
import com.numble.domain.response.UserInfoVO;
import com.numble.persister.MyBoxCollectPersister;
import com.numble.persister.MyBoxSeekPersister;
import com.numble.vo.AuthUserVO;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@AllArgsConstructor
@Slf4j
public class MyBoxDataLifeCycle {

    private final MyBoxSeekPersister seekPersister;

    private final MyBoxCollectPersister collectPersister;

    public AuthUserVO getApiUser(String userId) {
        return seekPersister.getApiUser(userId);
    }

    public int registerUser(UserAuthReq userAuthReq) {
        return collectPersister.registerUser(userAuthReq);
    }

    public UserInfoVO getUserDetailInfo(String userId) {
        return seekPersister.getUserDetailInfo(userId);
    }
}
