package com.numble.service;


import com.numble.feign.FileApiInterface;
import com.numble.domain.response.BusinessException;
import com.numble.domain.response.StatusCode;
import com.numble.utils.PropertyUtils;
import feign.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {


    private  FileApiInterface fileApiInterface;

    private final String token;
    private Random random;

    @Autowired
    public FileService(FileApiInterface fileApiInterface) {
        this.token = PropertyUtils.getString("file.token");
        this.random = new SecureRandom();
        this.fileApiInterface = fileApiInterface;
    }


    public Response upload(File file) {
        Response dataResponse = fileApiInterface.uploadFile(file,token);
        if (dataResponse == null) {
            log.info("file api fail");
            return null;
        }
        int status = dataResponse.status();
        log.info("file api success : " + status);
        return dataResponse;
    }


    public String getUploadFilePath(MultipartFile file) {
        return uploadFileAndGetPath(file);
    }

    public String uploadFileAndGetPath(MultipartFile file) {
        try {
            InputStream fileStream = file.getInputStream();
            File targetFile = createFileFromStream(fileStream,file.getOriginalFilename());
            return uploadAndGetPath(targetFile);
        } catch (IOException e) {
            throw new BusinessException(StatusCode.FILE_UPLOAD_ERROR);
        }
    }

    public File createFileFromStream(InputStream fileStream,String fileName) throws IOException {
        String filePath = PropertyUtils.getString("dir.upload.local") + setKeyFile(fileName);
        File targetFile = new File(filePath);
        FileUtils.copyInputStreamToFile(fileStream,targetFile);
        // todo 버퍼
        return targetFile;
    }

    public String setKeyFile(String fileName) {
        String date = DateFormatUtils.format(new Date(),"yyyyMMdd");
        int randInt = random.nextInt(100000);
        String ext = fileName.substring(fileName.lastIndexOf(".") +1);
        return String.format("$s_$s.$s",date,randInt,ext);
    }

    private String uploadAndGetPath(File file) {
        //String folder = PropertyUtils.getString("dir.upload.local");
        Response response = upload(file);
        int status = response.status();
        if (status != 200) {
            log.info("file server fail : " + status);
            return null;
        }
        return "http://175.125.177.207:12547" + "/" + file.getName();
    }


}

