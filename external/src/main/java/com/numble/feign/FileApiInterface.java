package com.numble.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;
import java.io.File;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient(name = "FileApiInterface"
        , url = "${feign.file-post-api.url}"
        , configuration = {FeignAutoConfiguration.class})
@Component
public interface FileApiInterface {

    @RequestLine("POST /upload?token={token}")
    @Headers("Content-Type: multipart/form-data")
    Response uploadFile(@Param("file")File file,@Param("token")String token);

}
