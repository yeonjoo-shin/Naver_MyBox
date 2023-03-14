package com.numble.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/numble/*")
@RequiredArgsConstructor
public class SystemController {

    @GetMapping(value = {"/healthCheck"})
    @ResponseBody
    public Object isRun() throws UnknownHostException {
        return String.valueOf("api application is Run! Host : " + InetAddress.getLocalHost().getHostName());
    }

}
