package com.numble.persister;

import com.numble.vo.AuthUserVO;

public interface MyBoxSeekPersister {

    AuthUserVO getApiUser(String userName);
}
