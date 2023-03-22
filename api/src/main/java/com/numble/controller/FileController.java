package com.numble.controller;

import com.numble.domain.response.CommonResponse;
import com.numble.domain.response.StatusCode;
import com.numble.service.FileService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/file/*")
public class FileController {

    private final FileService fileService;

    @ApiOperation(value = "파일 업로드")
    @PostMapping(value = "/upload")
    public ResponseEntity<CommonResponse<Object>> fileUpload(@RequestPart("file")MultipartFile file) {
        return ResponseEntity.ok().body(new CommonResponse<>(fileService.getUploadFilePath(file),StatusCode.SUCCESS_INSERT));
    }

}
