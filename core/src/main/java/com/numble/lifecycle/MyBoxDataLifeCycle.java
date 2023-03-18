package com.numble.lifecycle;

import com.numble.domain.request.UserAuthReq;
import com.numble.persister.MyBoxCollectPersister;
import com.numble.persister.MyBoxSeekPersister;
import com.numble.vo.AuthUserVO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MyBoxDataLifeCycle {

    private final MyBoxSeekPersister seekPersister;

    private final MyBoxCollectPersister collectPersister;

    public AuthUserVO getApiUser(String userName) {
        return seekPersister.getApiUser(userName);
    }

    public int registerUser(UserAuthReq userAuthReq) {
        return collectPersister.registerUser(userAuthReq);
    }
}
