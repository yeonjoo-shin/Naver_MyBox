package com.numble.persister;

import com.numble.domain.request.UserAuthReq;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MyBoxCollectPersister {

    int registerUser(UserAuthReq userAuthReq);
}
