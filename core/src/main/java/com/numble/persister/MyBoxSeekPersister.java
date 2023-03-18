package com.numble.persister;

import com.numble.vo.AuthUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MyBoxSeekPersister {

    AuthUserVO getApiUser(String userName);
}
