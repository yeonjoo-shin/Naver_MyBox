package com.numble.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Getter
public class DataSourceFactoryConfig {

    private SqlSessionFactory sqlSessionFactory;
    private String pathInfo;

    public  DataSourceFactoryConfig(String pathInfo) {
        this.pathInfo = "application.yml";
        this.sqlSessionFactory = getSessionFactory();
    }
    public SqlSessionFactory getSessionFactory() {
        try {
            InputStream inputStream = Resources.getResourceAsStream(pathInfo);
            SqlSessionFactory sessionFactory = (new SqlSessionFactoryBuilder()).build(inputStream);

            return sessionFactory;
        }catch (IOException var4) {
            log.error("error"+var4);
        }
        return null;
    }

}
