package com.numble.feign;


import feign.Contract;
import feign.codec.Encoder;
import feign.form.FormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    final ObjectFactory<HttpMessageConverters> messageConverters;

    public FeignConfig(
            ObjectFactory<HttpMessageConverters> messageConverters) {
        this.messageConverters = messageConverters;
    }

    @Bean
    public Contract feignContract() { return new Contract.Default();}

    @Bean
    public Encoder feignEncoder() { return new FormEncoder(new SpringEncoder(this.messageConverters));}

}
